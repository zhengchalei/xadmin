package com.zhengchalei.cloud.platform.config

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.format.Formatter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Configuration
class JacksonConfig : WebMvcConfigurer {
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")

    @Bean
    fun jackson2ObjectMapperBuilderCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder ->
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
    }


    @Bean
    fun localDateTimeFormatter(): Formatter<LocalDateTime> {
        return object : Formatter<LocalDateTime> {
            override fun parse(text: String, locale: java.util.Locale): LocalDateTime {
                return LocalDateTime.parse(text, dateTimeFormatter)
            }

            override fun print(t: LocalDateTime, locale: java.util.Locale): String {
                return t.format(dateTimeFormatter)
            }
        }
    }

    @Bean
    fun localDateFormatter(): Formatter<LocalDate> {
        return object : Formatter<LocalDate> {
            override fun parse(text: String, locale: java.util.Locale): LocalDate {
                return LocalDate.parse(text, dateFormatter)
            }

            override fun print(t: LocalDate, locale: java.util.Locale): String {
                return t.format(dateFormatter)
            }
        }
    }

    @Bean
    fun localTimeFormatter(): Formatter<LocalTime> {
        return object : Formatter<LocalTime> {
            override fun parse(text: String, locale: java.util.Locale): LocalTime {
                return LocalTime.parse(text, timeFormatter)
            }

            override fun print(t: LocalTime, locale: java.util.Locale): String {
                return t.format(timeFormatter)
            }
        }
    }
}