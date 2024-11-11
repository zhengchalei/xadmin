/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.aspect

import com.fasterxml.jackson.databind.ObjectMapper
import com.zhengchalei.cloud.platform.config.IP2RegionService
import com.zhengchalei.cloud.platform.config.IPUtil
import com.zhengchalei.cloud.platform.config.log.Log
import com.zhengchalei.cloud.platform.config.security.SecurityUtils
import com.zhengchalei.cloud.platform.modules.sys.domain.HttpMethod
import com.zhengchalei.cloud.platform.modules.sys.domain.SysOperationLog
import com.zhengchalei.cloud.platform.modules.sys.domain.SysUser
import com.zhengchalei.cloud.platform.modules.sys.domain.by
import com.zhengchalei.cloud.platform.modules.sys.repository.SysOperationLogRepository
import com.zhengchalei.cloud.platform.modules.sys.repository.SysUserRepository
import jakarta.servlet.http.HttpServletRequest
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
import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Aspect
@Component
class LoggingAspect(
    private val sysOperationLogRepository: SysOperationLogRepository,
    private val objectMapper: ObjectMapper,
    private val sysUserRepository: SysUserRepository,
    private val httpServletRequest: HttpServletRequest,
    private val iP2RegionService: IP2RegionService,
) {

    private val log = LoggerFactory.getLogger(LoggingAspect::class.java)

    private val cachedUser = CurrentHashMap<String, SysUser>()

    @Before("execution(* com.zhengchalei.cloud.platform.modules..*.*(..))")
    fun logBefore(joinPoint: JoinPoint) {
        val signature = joinPoint.signature
        val className = signature.declaringTypeName
        val methodName = signature.name
        val args = joinPoint.args
        log.info("进入方法: {}.{}，参数: {}", className, methodName, args.contentDeepToString())
    }

    @AfterReturning(pointcut = "execution(* com.zhengchalei.cloud.platform.modules..*.*(..))", returning = "result")
    fun logAfterReturning(joinPoint: JoinPoint, result: Any) {
        val signature = joinPoint.signature
        val className = signature.declaringTypeName
        val methodName = signature.name
        log.debug("离开方法: {}.{}，结果: {}", className, methodName, result)
    }

    @Around("@annotation(com.zhengchalei.cloud.platform.config.log.Log)")
    fun aroundAdvice(joinPoint: ProceedingJoinPoint): Any? {
        val signature: MethodSignature = joinPoint.signature as MethodSignature
        // 获取 URL
        val requestData = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(joinPoint.args.toList())
        val ipAddress = IPUtil.getIpAddress(httpServletRequest)
        val requestURI = httpServletRequest.requestURI
        val httpMethod = httpServletRequest.method
        // 获取 Log 注解
        val method: Method = signature.method
        val log = method.getAnnotation(Log::class.java)

        var result: Any? = null
        var exception: Exception? = null
        
        val status = try {
            result = joinPoint.proceed()
            true
        } catch (e: Exception) {
            exception = e
            false
        }
        // 异步存储日志
        Thread.startVirtualThread {
            val responseData = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result)
            val cachedUser = cachedUser.getOrPut(user?.id.toString()) { sysUserRepository.findByUsername(SecurityUtils.getCurrentUsername()) }
            val operationLog =
                new(SysOperationLog::class).by {
                    this.user = if (cachedUser == null) null else makeIdOnly(SysUser::class, cachedUser.id)
                    this.requestData = requestData
                    this.methodReference = "${signature.declaringTypeName}.${signature.name}"
                    this.httpMethod = HttpMethod.valueOf(httpMethod)
                    this.name = log.value
                    this.operationType = log.type
                    this.url = requestURI
                    this.ip = ipAddress
                    this.address = iP2RegionService.search(ipAddress)
                    this.responseData = responseData
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
