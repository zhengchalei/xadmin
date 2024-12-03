/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.config.security

import org.slf4j.LoggerFactory
import org.springframework.core.task.TaskDecorator
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder

class SecurityContextTaskDecorator : TaskDecorator {
    private val log = LoggerFactory.getLogger(SecurityContextTaskDecorator::class.java)

    override fun decorate(runnable: Runnable): Runnable {
        val context: SecurityContext = SecurityContextHolder.getContext()
        return Runnable {
            try {
                SecurityContextHolder.setContext(context)
                runnable.run()
            } catch (e: Exception) {
                log.error("异步执行失败", e)
            } finally {
                SecurityContextHolder.clearContext()
            }
        }
    }
}
