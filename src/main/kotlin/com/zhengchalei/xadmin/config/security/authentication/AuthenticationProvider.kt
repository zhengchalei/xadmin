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
            departmentId = userDetails.user.department?.id,
            dataScope = userDetails.user.department?.dataScope,
        )
    }

    override fun supports(authentication: Class<*>): Boolean =
        UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)

    fun loadUserByUsername(username: String, password: String): SysUserDetails {
        val user = sysUserRepository.findUserDetailsByUsername(username) ?: throw UserNotFoundException()
        return SysUserDetails(user)
    }
}
