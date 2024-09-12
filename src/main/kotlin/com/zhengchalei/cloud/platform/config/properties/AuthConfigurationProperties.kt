package com.zhengchalei.cloud.platform.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("auth")
class AuthConfigurationProperties {

    val type: AuthTokenType = AuthTokenType.JWT

    val store: StoreType = StoreType.MEMORY

    val jwt: JwtConfigurationProperties = JwtConfigurationProperties()
}

class JwtConfigurationProperties {
    var secret: String = "nnaWuft6pSSKVkcuzlmqBWi3vO4Cin44"

    // 单位秒 默认 24 小时
    var expired: Long = 60 * 60 * 24
}

enum class StoreType {
    MEMORY,
    REDIS,
    DATABASE,
}

enum class AuthTokenType {
    JWT,
    UUID,
}
