package com.zhengchalei.cloud.platform.config.security.provider

import com.zhengchalei.cloud.platform.config.security.AuthenticationToken
import org.springframework.security.core.Authentication

interface AuthTokenProvider {

    fun createToken(authentication: AuthenticationToken): String

    fun validateToken(token: String): Boolean

    fun getAuthentication(token: String): Authentication

}