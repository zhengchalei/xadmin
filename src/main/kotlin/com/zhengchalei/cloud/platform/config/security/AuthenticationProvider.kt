/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.config.security

import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.config.UserDisabledException
import com.zhengchalei.cloud.platform.config.UserNotFoundException
import com.zhengchalei.cloud.platform.config.UserPasswordErrorException
import com.zhengchalei.cloud.platform.config.security.authentication.SysUserDetails
import com.zhengchalei.cloud.platform.modules.sys.repository.SysUserRepository
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
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
        if (!passwordEncoder.matches(password, user.password)) throw UserPasswordErrorException()
        if (!user.status) throw UserDisabledException()
        if (!passwordEncoder.matches(password, user.password)) throw UserPasswordErrorException()
        val authorityList = mutableListOf<GrantedAuthority>()
        val roles = user.roles
        val permissions = user.roles.flatMap { it.permissions }
        authorityList.addAll(permissions.map { it.code }.map { SimpleGrantedAuthority(it) })
        authorityList.addAll(
            roles.map { it.code }.map { Const.SecurityRolePrifix + it }.map { SimpleGrantedAuthority(it) }
        )
        return SysUserDetails(user)
    }
}
