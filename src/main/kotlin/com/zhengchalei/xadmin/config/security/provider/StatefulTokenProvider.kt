/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.config.security.provider

import com.zhengchalei.xadmin.config.exceptions.NotLoginException
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

    override fun createToken(authentication: SysUserAuthentication): String {
        val token = UUID.randomUUID().toString()
        val statefulTokenUser =
            StatefulTokenUser(
                authentication.id,
                authentication.username,
                authentication.password,
                authentication.authorities.map { it.authority },
            )
        tokenMap[token] = statefulTokenUser
        return token
    }

    override fun validateToken(token: String): Boolean {
        return tokenMap.containsKey(token)
    }

    override fun getAuthentication(token: String): Authentication {
        val statefulTokenUser: StatefulTokenUser = tokenMap[token] ?: throw NotLoginException()
        return SysUserAuthentication(
            statefulTokenUser.id,
            statefulTokenUser.username,
            statefulTokenUser.password,
            statefulTokenUser.authorities.map { SimpleGrantedAuthority(it) },
        )
    }

    override fun logout(token: String) {
        tokenMap.remove(token)
    }
}

data class StatefulTokenUser(val id: Long, val username: String, val password: String, val authorities: List<String>)
