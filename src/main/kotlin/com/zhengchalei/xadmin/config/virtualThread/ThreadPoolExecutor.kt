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
