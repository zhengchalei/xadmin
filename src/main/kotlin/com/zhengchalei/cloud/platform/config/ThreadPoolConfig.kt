package com.zhengchalei.cloud.platform.config

import com.zhengchalei.cloud.platform.config.security.SecurityContextTaskDecorator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.scheduling.annotation.EnableAsync

class VirtualThreadExecutor : SimpleAsyncTaskExecutor()

@EnableAsync
@Configuration
class ThreadPoolConfig {
    @Bean
    fun virtualThreadExecutor(): VirtualThreadExecutor {
        val virtualThreadExecutor = VirtualThreadExecutor()
        virtualThreadExecutor.setVirtualThreads(true)
        virtualThreadExecutor.setTaskDecorator(SecurityContextTaskDecorator())
        virtualThreadExecutor.threadFactory = Thread.ofVirtual().name("virtual-business-executor-", 0).factory()
        virtualThreadExecutor.setTaskTerminationTimeout(5000)
        return virtualThreadExecutor
    }
}
