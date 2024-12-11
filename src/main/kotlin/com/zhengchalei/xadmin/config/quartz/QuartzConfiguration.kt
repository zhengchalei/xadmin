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
package com.zhengchalei.xadmin.config.quartz

import com.zhengchalei.xadmin.config.virtualThread.ThreadPoolExecutorConfig
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
        threadPoolExecutorConfig: ThreadPoolExecutorConfig,
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
        schedulerFactoryBean.setTaskExecutor(threadPoolExecutorConfig.virtualThreadExecutor("Quartz"))
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
