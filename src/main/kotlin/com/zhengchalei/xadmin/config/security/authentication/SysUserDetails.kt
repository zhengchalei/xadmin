/*
Copyright 2024 [郑查磊]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.zhengchalei.xadmin.config.security.authentication

import com.zhengchalei.xadmin.commons.Const
import com.zhengchalei.xadmin.modules.sys.domain.SysUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class SysUserDetails(val user: SysUser) : UserDetails {

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
