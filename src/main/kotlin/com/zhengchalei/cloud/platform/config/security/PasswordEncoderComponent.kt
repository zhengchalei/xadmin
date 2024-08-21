package com.zhengchalei.cloud.platform.config.security

import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoderComponent {


    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(16)
    }


}