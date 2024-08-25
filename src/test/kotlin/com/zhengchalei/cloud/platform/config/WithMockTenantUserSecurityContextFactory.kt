package com.zhengchalei.cloud.platform.config

import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.config.security.TenantCaptchaAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.test.context.support.WithSecurityContextFactory
import java.util.*

class WithMockTenantUserSecurityContextFactory : WithSecurityContextFactory<WithMockTenantUser> {
    override fun createSecurityContext(annotation: WithMockTenantUser): SecurityContext {
        val context = SecurityContextHolder.createEmptyContext()
        val authorities: MutableList<GrantedAuthority> = ArrayList()
        authorities.addAll(
            Arrays
                .stream(annotation.authorities)
                .map { SimpleGrantedAuthority(it) }
                .toList(),
        )
        authorities.addAll(
            Arrays
                .stream(annotation.roles)
                .map { Const.SecurityRolePrifix + it }
                .map { SimpleGrantedAuthority(it) }
                .toList(),
        )
        val principal: UserDetails = User(annotation.username, annotation.password, authorities)
        val auth: Authentication =
            TenantCaptchaAuthenticationToken(principal, annotation.password, annotation.tenant, "", authorities)
        context.authentication = auth
        return context
    }
}
