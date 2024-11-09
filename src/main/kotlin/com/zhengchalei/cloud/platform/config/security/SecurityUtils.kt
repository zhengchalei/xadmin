/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
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

    private fun getTenantCaptchaAuthenticationToken(): AuthenticationToken {
        if ((SecurityContextHolder.getContext().authentication is AuthenticationToken).not()) {
            throw NotLoginException()
        }
        return SecurityContextHolder.getContext().authentication as AuthenticationToken
    }

    // 判断是否登录
    fun isLogin(): Boolean = SecurityContextHolder.getContext().authentication is AuthenticationToken
}
