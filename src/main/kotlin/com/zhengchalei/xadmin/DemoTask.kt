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
package com.zhengchalei.xadmin

import org.quartz.*
import org.slf4j.Logger
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class DemoTask {
    private val log: Logger = org.slf4j.LoggerFactory.getLogger(DemoTask::class.java)

    class DemoJob : Job {
        private val log: Logger = org.slf4j.LoggerFactory.getLogger(DemoJob::class.java)

        override fun execute(context: JobExecutionContext) {
            // 判断当前是否为虚拟线程

            log.info(
                "DemoJob execute,线程名称: ${Thread.currentThread().name} 是否为虚拟线程: ${Thread.currentThread().isVirtual}"
            )
        }
    }

    @Bean
    fun demoJobDetail(): JobDetail {
        return JobBuilder.newJob(DemoJob::class.java).storeDurably().build()
    }

    @Bean
    fun demoTrigger(demoJobDetail: JobDetail): Trigger {
        return TriggerBuilder.newTrigger()
            .forJob(demoJobDetail)
            .withSchedule(
                CronScheduleBuilder
                    // 每5秒执行一次
                    .cronSchedule("0/5 * * * * ?")
            )
            .build()
    }
}
