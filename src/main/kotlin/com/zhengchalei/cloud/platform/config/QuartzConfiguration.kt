/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.config

import java.util.*
import org.quartz.Calendar
import org.quartz.JobDetail
import org.quartz.Trigger
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.quartz.QuartzProperties
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.scheduling.quartz.SpringBeanJobFactory

@Configuration
class QuartzConfiguration {

    @Bean
    fun quartzScheduler(
        properties: QuartzProperties,
        customizers: ObjectProvider<SchedulerFactoryBeanCustomizer>,
        jobDetails: ObjectProvider<JobDetail>,
        calendars: Map<String, Calendar>,
        triggers: ObjectProvider<Trigger>,
        applicationContext: ApplicationContext,
    ): SchedulerFactoryBean {
        val schedulerFactoryBean = SchedulerFactoryBean()
        val jobFactory = SpringBeanJobFactory()
        jobFactory.setApplicationContext(applicationContext)
        schedulerFactoryBean.setJobFactory(jobFactory)
        if (properties.schedulerName != null) {
            schedulerFactoryBean.setSchedulerName(properties.schedulerName)
        }

        schedulerFactoryBean.isAutoStartup = properties.isAutoStartup
        schedulerFactoryBean.setStartupDelay(properties.startupDelay.seconds.toInt())
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(properties.isWaitForJobsToCompleteOnShutdown)
        schedulerFactoryBean.setOverwriteExistingJobs(properties.isOverwriteExistingJobs)
        schedulerFactoryBean.setTaskExecutor(virtualThreadExecutor("quartz"))
        if (properties.properties.isNotEmpty()) {
            schedulerFactoryBean.setQuartzProperties(this.asProperties(properties.properties))
        }
        schedulerFactoryBean.setCalendars(calendars)
        schedulerFactoryBean.setJobDetails(*jobDetails.orderedStream().toList().toTypedArray())
        schedulerFactoryBean.setTriggers(*triggers.orderedStream().toList().toTypedArray())
        customizers.orderedStream().forEach { customizer -> customizer.customize(schedulerFactoryBean) }
        return schedulerFactoryBean
    }

    private fun asProperties(source: Map<String?, String?>): Properties {
        val properties = Properties()
        properties.putAll(source)
        return properties
    }
}
