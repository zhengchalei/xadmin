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
import com.zhengchalei.cloud.platform.modules.sys.repository.SysUserRepository
import net.dreamlu.mica.captcha.service.ICaptchaService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
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
    private val sysUserRepository: SysUserRepository, private val passwordEncoder: PasswordEncoder,
    private val captchaService: ICaptchaService,
) :
    AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val password = authentication.credentials as String
        val captcha = (authentication as AuthenticationToken).captcha
        val captchaUUID = (authentication as AuthenticationToken).captchaID
        // 验证租户ID、验证码和用户密码的逻辑
        if (isValidCaptcha(captcha, captchaUUID)) {
            val userDetails = loadUserByUsername(username, password)
            return AuthenticationToken(
                username = username,
                password = password,
                captcha = captcha,
                captchaID = captchaUUID,
                authorities = userDetails.authorities,
            )
        }
        throw UserPasswordErrorException()
    }

    override fun supports(authentication: Class<*>): Boolean =
        AuthenticationToken::class.java.isAssignableFrom(authentication)

    private fun isValidCaptcha(captcha: String, captchaUUID: String): Boolean {
        // TODO 这里实现验证码验证逻辑
        return captchaService.validate(captchaUUID, captcha)
    }

    fun loadUserByUsername(username: String, password: String): UserDetails {
        val user = sysUserRepository.findByUsername(username) ?: throw UserNotFoundException()
        if (user.username == Const.AdminUser) {
            if (!passwordEncoder.matches(password, user.password)) throw UserPasswordErrorException()
            return User(
                username,
                user.password,
                true,
                true,
                true,
                true,
                mutableListOf(SimpleGrantedAuthority(Const.SecurityRolePrifix + Const.AdminRole)),
            )
        }
        if (!user.status) throw UserDisabledException()
        if (!passwordEncoder.matches(password, user.password)) throw UserPasswordErrorException()
        val authorityList = mutableListOf<GrantedAuthority>()
        val roles = user.roles
        val permissions = user.roles.flatMap { it.permissions }
        authorityList.addAll(permissions.map { it.code }.map { SimpleGrantedAuthority(it) })
        authorityList.addAll(
            roles.map { it.code }.map { Const.SecurityRolePrifix + it }.map { SimpleGrantedAuthority(it) }
        )
        return User(username, user.password, user.status, true, true, true, authorityList)
    }
}
