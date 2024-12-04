/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
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
