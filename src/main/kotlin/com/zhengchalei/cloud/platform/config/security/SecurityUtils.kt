package com.zhengchalei.cloud.platform.config.security

import com.zhengchalei.cloud.platform.config.NotLoginException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails


object SecurityUtils {

    fun getCurrentUsername(): String {
        val authentication: Authentication =
            SecurityContextHolder.getContext().authentication ?: throw NotLoginException()

        when (authentication.principal) {
            is UserDetails -> {
                val userDetails = authentication.principal as UserDetails
                return userDetails.username
            }

            is String -> {
                return authentication.principal as String
            }

            else -> {
                throw NotLoginException()
            }
        }
    }

    fun getTenantCaptchaAuthenticationToken(): TenantCaptchaAuthenticationToken {
        if ((SecurityContextHolder.getContext().authentication is TenantCaptchaAuthenticationToken).not()) {
            throw NotLoginException()
        }
        return SecurityContextHolder.getContext().authentication as TenantCaptchaAuthenticationToken
    }

    fun getCurrentTenant(): String {
        return getTenantCaptchaAuthenticationToken().tenant
    }

    // 判断是否登录
    fun isLogin(): Boolean {
        return SecurityContextHolder.getContext().authentication is TenantCaptchaAuthenticationToken
    }

}