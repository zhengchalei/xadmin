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
package com.zhengchalei.xadmin.config.email

import freemarker.cache.ClassTemplateLoader
import freemarker.cache.TemplateLoader
import freemarker.template.Configuration
import freemarker.template.Template
import java.io.StringWriter
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer

@Service
class EmailService(
    private val javaMailSender: JavaMailSender,
    @Value("\${spring.mail.username}") private val emailFrom: String,
) {

    private val freemarkerConfiguration: FreeMarkerConfigurer = freemarkerGeneratorConfig()

    private final fun freemarkerGeneratorConfig(): FreeMarkerConfigurer {
        val configuration = Configuration(Configuration.VERSION_2_3_33)
        val templateLoader: TemplateLoader = ClassTemplateLoader(this.javaClass, "/templates/email-templates")
        configuration.templateLoader = templateLoader
        val freeMarkerConfigurer = FreeMarkerConfigurer()
        freeMarkerConfigurer.configuration = configuration
        return freeMarkerConfigurer
    }

    @Async
    fun sendVerificationCode(email: String, code: String) {
        val template: Template = freemarkerConfiguration.configuration.getTemplate("verification-code.ftl")
        val model = mapOf("code" to code) // 模板变量
        val text =
            StringWriter().use { writer ->
                template.process(model, writer)
                writer.toString()
            }

        val message = javaMailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setFrom(emailFrom)
        helper.setTo(email)
        helper.setSubject("xadmin验证码")
        helper.setText(text, true)
        javaMailSender.send(message)
    }
}
