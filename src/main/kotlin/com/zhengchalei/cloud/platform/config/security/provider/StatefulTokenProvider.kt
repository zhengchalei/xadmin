package com.zhengchalei.cloud.platform.config.security.provider

import com.zhengchalei.cloud.platform.config.NotLoginException
import com.zhengchalei.cloud.platform.config.security.AuthenticationToken
import io.lettuce.core.RedisClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.UUID

@Component
@ConditionalOnProperty(value = ["auth.token-type"], havingValue = "Stateful", matchIfMissing = false)
class StatefulTokenProvider(
    private val redisTemplate: RedisTemplate<String, String>
): AuthTokenProvider {
    override fun createToken(authentication: AuthenticationToken): String {
        val token = UUID.randomUUID().toString()
        redisTemplate.opsForValue().set(token, authentication.name)
        return token
    }

    override fun validateToken(token: String): Boolean {
        return redisTemplate.opsForValue().get(token) != null
    }

    override fun getAuthentication(token: String): Authentication {
        return AuthenticationToken(
            username = redisTemplate.opsForValue().get(token) ?: throw NotLoginException(),
            password = "",
            authorities = emptyList(),
            captcha = "",
            captchaID = ""
        )
    }


}