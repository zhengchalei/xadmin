package com.zhengchalei.cloud.platform.config.security

import org.springframework.core.task.TaskDecorator
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder

class SecurityContextTaskDecorator : TaskDecorator {
    override fun decorate(runnable: Runnable): Runnable {
        val context: SecurityContext = SecurityContextHolder.getContext()
        return Runnable {
            try {
                SecurityContextHolder.setContext(context)
                runnable.run()
            } finally {
                SecurityContextHolder.clearContext()
            }
        }
    }
}
