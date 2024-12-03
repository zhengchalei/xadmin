package com.zhengchalei.cloud.platform.config.security.provider

import com.zhengchalei.cloud.platform.config.security.SysUserAuthentication
import org.springframework.security.core.Authentication

interface AuthTokenProvider {

    fun createToken(authentication: SysUserAuthentication): String

    fun validateToken(token: String): Boolean

    fun getAuthentication(token: String): Authentication

    fun logout(token: String)
}