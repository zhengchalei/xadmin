/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.config.virtualThread

import java.util.*
import org.slf4j.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder

class VirtualThreadExecutor : SimpleAsyncTaskExecutor() {
    val log: Logger = org.slf4j.LoggerFactory.getLogger(javaClass)
}

@EnableAsync
@Configuration
class ThreadPoolExecutorConfig {
    @Bean
    fun virtualThreadExecutor(): VirtualThreadExecutor {
        return virtualThreadExecutor("business")
    }

    fun virtualThreadExecutor(name: String = UUID.randomUUID().toString()): VirtualThreadExecutor {
        val virtualThreadExecutor = VirtualThreadExecutor()
        virtualThreadExecutor.setVirtualThreads(true)
        virtualThreadExecutor.setTaskDecorator { runnable ->
            val context: SecurityContext = SecurityContextHolder.getContext()
            Runnable {
                try {
                    SecurityContextHolder.setContext(context)
                    runnable.run()
                } catch (e: Exception) {
                    virtualThreadExecutor.log.error("异步执行失败", e)
                } finally {
                    SecurityContextHolder.clearContext()
                }
            }
        }
        virtualThreadExecutor.threadFactory = Thread.ofVirtual().name("virtual-$name-executor", 0).factory()
        virtualThreadExecutor.setTaskTerminationTimeout(5000)
        return virtualThreadExecutor
    }
}
