/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.config.virtualThread

import com.zhengchalei.xadmin.config.security.SecurityContextTaskDecorator
import java.util.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.scheduling.annotation.EnableAsync

class VirtualThreadExecutor : SimpleAsyncTaskExecutor()

@EnableAsync
@Configuration
class ThreadPoolConfig {
    @Bean
    fun businessVirtualThreadExecutor(): VirtualThreadExecutor {
        return virtualThreadExecutor("business")
    }
}

fun virtualThreadExecutor(name: String = UUID.randomUUID().toString()): VirtualThreadExecutor {
    val virtualThreadExecutor = VirtualThreadExecutor()
    virtualThreadExecutor.setVirtualThreads(true)
    virtualThreadExecutor.setTaskDecorator(SecurityContextTaskDecorator())
    virtualThreadExecutor.threadFactory = Thread.ofVirtual().name("virtual-$name-executor", 0).factory()
    virtualThreadExecutor.setTaskTerminationTimeout(5000)
    return virtualThreadExecutor
}
