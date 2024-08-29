package com.zhengchalei.cloud.platform.config.security

import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.config.*
import com.zhengchalei.cloud.platform.modules.sys.domain.SysTenant
import com.zhengchalei.cloud.platform.modules.sys.domain.code
import com.zhengchalei.cloud.platform.modules.sys.domain.id
import com.zhengchalei.cloud.platform.modules.sys.repository.SysTenantRepository
import com.zhengchalei.cloud.platform.modules.sys.repository.SysUserRepository
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class TenantCaptchaAuthenticationProvider(
    val sysTenantRepository: SysTenantRepository,
    val sysUserRepository: SysUserRepository,
    val passwordEncoder: PasswordEncoder,
) : AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.name
        val password = authentication.credentials as String
        val tenant = (authentication as TenantCaptchaAuthenticationToken).tenant
        val captcha = authentication.captcha
        // 验证租户ID、验证码和用户密码的逻辑
        if (isValidTenant(tenant) && isValidCaptcha(captcha)) {
            val userDetails = loadUserByUsername(username, password, tenant)
            return TenantCaptchaAuthenticationToken(username, password, tenant, captcha, userDetails.authorities)
        } else {
            throw UserPasswordErrorException()
        }
    }

    override fun supports(authentication: Class<*>): Boolean = TenantCaptchaAuthenticationToken::class.java.isAssignableFrom(authentication)

    private fun isValidTenant(tenant: String): Boolean {
        // 这里实现租户ID验证逻辑
        this.sysTenantRepository.sql
            .createQuery(SysTenant::class) {
                where(table.code eq tenant)
                select(table.id)
            }.fetchOneOrNull() ?: throw TenantNotFoundException()
        return true
    }

    private fun isValidCaptcha(captcha: String): Boolean {
        // TODO 这里实现验证码验证逻辑
        return true ?: throw CaptchaErrorException()
    }

    fun loadUserByUsername(
        username: String,
        password: String,
        tenant: String,
    ): UserDetails {
        if (username == Const.SuperAdmin) {
            val user = sysUserRepository.findByUsername(username) ?: throw UserNotFoundException()
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

        val user = sysUserRepository.findByUsernameAndTenant(username, tenant) ?: throw UserNotFoundException()
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
            roles
                .map { it.code }
                .map { Const.SecurityRolePrifix + it }
                .map { SimpleGrantedAuthority(it) },
        )
        return User(username, user.password, user.status, true, true, true, authorityList)
    }
}
