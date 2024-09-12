package com.zhengchalei.cloud.platform.config.security

import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.config.NotLoginException
import com.zhengchalei.cloud.platform.config.SwitchTenantException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
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

    private fun getTenantCaptchaAuthenticationToken(): TenantAuthenticationToken {
        if (
            (SecurityContextHolder.getContext().authentication is TenantAuthenticationToken).not()
        ) {
            throw NotLoginException()
        }
        return SecurityContextHolder.getContext().authentication as TenantAuthenticationToken
    }

    fun getCurrentTenant(): String = getTenantCaptchaAuthenticationToken().tenant

    // 判断是否登录
    fun isLogin(): Boolean =
        SecurityContextHolder.getContext().authentication is TenantAuthenticationToken

    /**
     * 切换租户
     *
     * @param [tenant] 租户
     */
    fun switchTenant(tenant: String, jwtProvider: JwtProvider): String {
        // 判断用户名是否为 superAdmin
        if (
            (SecurityContextHolder.getContext().authentication.principal as User).username ==
                Const.Root
        ) {
            // 切换租户
            SecurityContextHolder.getContext().authentication =
                TenantAuthenticationToken(
                    username = Const.Root,
                    password = "",
                    captcha = "",
                    tenant = tenant,
                )
            val authentication: TenantAuthenticationToken =
                SecurityContextHolder.getContext().authentication as TenantAuthenticationToken
            val token: String = jwtProvider.createAccessToken(authentication)
            return token
        } else {
            throw SwitchTenantException()
        }
    }
}
