/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.config.security.authentication

import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.modules.sys.domain.SysUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class SysUserDetails(private val user: SysUser) : UserDetails {

    val id: Long = user.id

    val roles = user.roles.map { it.code }

    val permissions = user.roles.map { it.permissions }.flatten().map { it.code }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorityList = mutableListOf<GrantedAuthority>()
        val roles = user.roles
        val permissions = user.roles.flatMap { it.permissions }
        authorityList.addAll(permissions.map { it.code }.map { SimpleGrantedAuthority(it) })
        authorityList.addAll(
            roles.map { it.code }.map { Const.SecurityRolePrifix + it }.map { SimpleGrantedAuthority(it) }
        )
        return authorityList
    }

    override fun getPassword(): String {
        return this.user.password
    }

    override fun getUsername(): String {
        return this.user.username
    }
}
