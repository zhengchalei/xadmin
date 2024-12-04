/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.config.security.authentication

import com.zhengchalei.xadmin.config.exceptions.UserNotFoundException
import com.zhengchalei.xadmin.modules.sys.repository.SysUserRepository
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

/**
 * authentication provider
 *
 * @property sysUserRepository
 * @property passwordEncoder
 * @constructor Create empty authentication provider
 */
@Component
class AuthenticationProvider(
    private val sysUserRepository: SysUserRepository,
    private val passwordEncoder: PasswordEncoder,
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val password = authentication.credentials as String
        // 验证租户ID、验证码和用户密码的逻辑
        val userDetails = loadUserByUsername(username, password)
        return SysUserAuthentication(
            id = userDetails.id,
            username = username,
            password = password,
            authorities = userDetails.authorities,
        )
    }

    override fun supports(authentication: Class<*>): Boolean =
        UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)

    fun loadUserByUsername(username: String, password: String): SysUserDetails {
        val user = sysUserRepository.findUserDetailsByUsername(username) ?: throw UserNotFoundException()
        return SysUserDetails(user)
    }
}
