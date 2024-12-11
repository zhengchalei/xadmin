/*
Copyright 2024 [郑查磊]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.zhengchalei.xadmin.config.aspect

import com.fasterxml.jackson.databind.ObjectMapper
import com.zhengchalei.xadmin.commons.Const
import com.zhengchalei.xadmin.commons.util.IPUtil
import com.zhengchalei.xadmin.config.virtualThread.VirtualThreadExecutor
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
    private val virtualThreadExecutor: VirtualThreadExecutor,
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
        virtualThreadExecutor.execute {
            val resultJson =
                if (profile == Const.ENV_DEV) objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result)
                else objectMapper.writeValueAsString(result)
            val operationLog =
                new(SysOperationLog::class).by {
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
