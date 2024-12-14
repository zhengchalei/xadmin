package com.zhengchalei.xadmin.config

import com.zhengchalei.xadmin.commons.Const
import com.zhengchalei.xadmin.config.security.authentication.SysUserAuthentication
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory
import java.util.*

class WithMockUserSecurityContextFactory : WithSecurityContextFactory<WithMockUser> {
    override fun createSecurityContext(annotation: WithMockUser): SecurityContext {
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
                .map { Const.SECURITY_ROLE_PREFIX + it }
                .map { SimpleGrantedAuthority(it) }
                .toList(),
        )
        val auth: Authentication =
            SysUserAuthentication(
                id = annotation.id,
                username = annotation.username,
                password = annotation.password,
                authorities = authorities,
                dataScope = annotation.dataScope,
                departmentId = annotation.departmentId
            )
        context.authentication = auth
        return context
    }
}
