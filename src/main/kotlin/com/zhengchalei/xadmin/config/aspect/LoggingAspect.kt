/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.config.aspect

import com.fasterxml.jackson.databind.ObjectMapper
import com.zhengchalei.xadmin.commons.Const
import com.zhengchalei.xadmin.commons.util.IPUtil
import com.zhengchalei.xadmin.config.security.SecurityUtils
import com.zhengchalei.xadmin.modules.sys.domain.*
import com.zhengchalei.xadmin.modules.sys.repository.SysOperationLogRepository
import com.zhengchalei.xadmin.modules.sys.repository.SysUserRepository
import jakarta.servlet.http.HttpServletRequest
import java.lang.reflect.Method
import java.util.concurrent.ConcurrentHashMap
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.babyfish.jimmer.kt.makeIdOnly
import org.babyfish.jimmer.kt.new
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect(
    private val sysOperationLogRepository: SysOperationLogRepository,
    private val objectMapper: ObjectMapper,
    private val sysUserRepository: SysUserRepository,
    private val httpServletRequest: HttpServletRequest,
    private val ip2regionSearcher: Ip2regionSearcher,
    @Value("spring.profiles.active") private val profile: String,
) {

    private val log = LoggerFactory.getLogger(LoggingAspect::class.java)

    private val cachedUser: Map<String, SysUser> = ConcurrentHashMap<String, SysUser>()

    @Before("execution(* com.zhengchalei.xadmin.modules..*.*(..))")
    fun logBefore(joinPoint: JoinPoint) {
        val signature = joinPoint.signature
        val className = signature.declaringTypeName
        val methodName = signature.name
        val args = joinPoint.args
        if (profile == Const.ENV_DEV) {
            log.info("进入方法: {}.{}，参数: {}", className, methodName, args.contentDeepToString())
        }
    }

    @AfterReturning(pointcut = "execution(* com.zhengchalei.xadmin.modules..*.*(..))", returning = "result")
    fun logAfterReturning(joinPoint: JoinPoint, result: Any?) {
        val signature = joinPoint.signature
        val className = signature.declaringTypeName
        val methodName = signature.name
        if (profile == Const.ENV_DEV) {
            log.info("离开方法: {}.{}，结果: {}", className, methodName, result)
        }
    }

    @Around("@annotation(com.zhengchalei.xadmin.modules.sys.domain.Log)")
    fun aroundAdvice(joinPoint: ProceedingJoinPoint): Any? {
        val signature: MethodSignature = joinPoint.signature as MethodSignature
        // 获取 URL
        val params = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(joinPoint.args.toList())
        val ipAddress = IPUtil.getIpAddress(httpServletRequest)
        val requestURI = httpServletRequest.requestURI
        val httpMethod = httpServletRequest.method
        // 获取 Log 注解
        val method: Method = signature.method
        val log = method.getAnnotation(Log::class.java)

        var result: Any? = null
        var exception: Exception? = null

        val status =
            try {
                result = joinPoint.proceed()
                true
            } catch (e: Exception) {
                exception = e
                false
            }
        // 异步存储日志
        Thread.startVirtualThread {
            val resultJson =
                if (profile == Const.ENV_DEV) objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result)
                else objectMapper.writeValueAsString(result)
            val cachedUser =
                cachedUser[SecurityUtils.getCurrentUsername()]?.let {
                    sysUserRepository.findUserDetailsByUsername(SecurityUtils.getCurrentUsername())
                }
            val operationLog =
                new(SysOperationLog::class).by {
                    this.user = if (cachedUser == null) null else makeIdOnly(SysUser::class, cachedUser.id)
                    this.params = params
                    this.methodReference = "${signature.declaringTypeName}.${signature.name}"
                    this.httpMethod = HttpMethod.valueOf(httpMethod)
                    this.name = log.value
                    this.operationType = log.type
                    this.url = requestURI
                    this.ip = ipAddress
                    this.address = ip2regionSearcher.getAddress(ipAddress)
                    this.result = resultJson
                    this.time = System.currentTimeMillis()
                    this.status = status
                    this.errorMessage = if (status) null else exception?.message
                    this.errorStack = exception?.stackTraceToString()
                }
            sysOperationLogRepository.insert(operationLog)
        }
        // 重新抛出异常，保证主流程不受影响
        exception?.let { throw it }
        return result
    }
}
