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
package com.zhengchalei.xadmin.config.jackson

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.Formatter

@Configuration
class JacksonConfig {
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    @Bean
    fun jackson2ObjectMapperBuilderCustomizer(): Jackson2ObjectMapperBuilderCustomizer =
        Jackson2ObjectMapperBuilderCustomizer { builder ->
            // LocalTime
            builder.serializerByType(LocalTime::class.java, LocalDateTimeSerializer(timeFormatter))
            builder.deserializerByType(LocalTime::class.java, LocalDateTimeDeserializer(timeFormatter))

            // LocalDate
            builder.serializerByType(LocalDateTime::class.java, LocalDateTimeSerializer(dateTimeFormatter))
            builder.deserializerByType(LocalDateTime::class.java, LocalDateTimeDeserializer(dateTimeFormatter))

            // LocalDateTime
            builder.serializerByType(LocalDateTime::class.java, LocalDateTimeSerializer(dateTimeFormatter))
            builder.deserializerByType(LocalDateTime::class.java, LocalDateTimeDeserializer(dateTimeFormatter))
        }

    @Bean
    fun localDateTimeFormatter(): Formatter<LocalDateTime> =
        object : Formatter<LocalDateTime> {
            override fun parse(text: String, locale: java.util.Locale): LocalDateTime =
                LocalDateTime.parse(text, dateTimeFormatter)

            override fun print(t: LocalDateTime, locale: java.util.Locale): String = t.format(dateTimeFormatter)
        }

    @Bean
    fun localDateFormatter(): Formatter<LocalDate> =
        object : Formatter<LocalDate> {
            override fun parse(text: String, locale: java.util.Locale): LocalDate = LocalDate.parse(text, dateFormatter)

            override fun print(t: LocalDate, locale: java.util.Locale): String = t.format(dateFormatter)
        }

    @Bean
    fun localTimeFormatter(): Formatter<LocalTime> =
        object : Formatter<LocalTime> {
            override fun parse(text: String, locale: java.util.Locale): LocalTime = LocalTime.parse(text, timeFormatter)

            override fun print(t: LocalTime, locale: java.util.Locale): String = t.format(timeFormatter)
        }
}
