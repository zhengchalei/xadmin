/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.config

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggingAspect {

    private val log = LoggerFactory.getLogger(LoggingAspect::class.java)

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
}
