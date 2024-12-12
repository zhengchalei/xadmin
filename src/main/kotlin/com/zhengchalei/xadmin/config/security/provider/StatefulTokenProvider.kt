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
package com.zhengchalei.xadmin.config.security.provider

import com.zhengchalei.xadmin.config.exceptions.NotLoginException
import com.zhengchalei.xadmin.config.jimmer.filter.DataScope
import com.zhengchalei.xadmin.config.security.SecurityUtils
import com.zhengchalei.xadmin.config.security.authentication.SysUserAuthentication
import java.util.*
import org.redisson.api.RedissonClient
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(value = ["auth.token-type"], havingValue = "Stateful", matchIfMissing = false)
class StatefulTokenProvider(private val redissonClient: RedissonClient) : AuthTokenProvider {

    private val tokenMap = redissonClient.getMap<String, StatefulTokenUser>("token")
    private val userMap = redissonClient.getMap<Long, String>("token:user")

    override fun createToken(authentication: SysUserAuthentication): String {
        val token = UUID.randomUUID().toString()
        val statefulTokenUser =
            StatefulTokenUser(
                id = authentication.id,
                username = authentication.username,
                password = authentication.password,
                departmentId = authentication.departmentId,
                dataScope = authentication.dataScope,
                authorities = authentication.authorities.map { it.authority },
            )
        tokenMap[token] = statefulTokenUser
        userMap[statefulTokenUser.id] = token
        return token
    }

    override fun validateToken(token: String): Boolean {
        return tokenMap.containsKey(token)
    }

    override fun getAuthentication(token: String): Authentication {
        val statefulTokenUser: StatefulTokenUser = tokenMap[token] ?: throw NotLoginException()
        return SysUserAuthentication(
            id = statefulTokenUser.id,
            username = statefulTokenUser.username,
            password = statefulTokenUser.password,
            departmentId = statefulTokenUser.departmentId,
            dataScope = statefulTokenUser.dataScope,
            authorities = statefulTokenUser.authorities.map { SimpleGrantedAuthority(it) },
        )
    }

    override fun logout() {
        val userId = SecurityUtils.getCurrentUserId()
        val token = userMap[userId]
        userMap.remove(userId)
        tokenMap.remove(token)
    }
}

data class StatefulTokenUser(
    val id: Long,
    val username: String,
    val password: String,
    val departmentId: Long?,
    val dataScope: DataScope?,
    val authorities: List<String>,
)
