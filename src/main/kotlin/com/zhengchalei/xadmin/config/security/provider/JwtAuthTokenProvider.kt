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

import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import com.zhengchalei.xadmin.commons.Const
import com.zhengchalei.xadmin.config.exceptions.InvalidTokenException
import com.zhengchalei.xadmin.config.exceptions.TokenInvalidException
import com.zhengchalei.xadmin.config.jimmer.filter.DataScope
import com.zhengchalei.xadmin.config.security.authentication.SysUserAuthentication
import java.util.*
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(value = ["auth.token-type"], havingValue = "JWT", matchIfMissing = false)
class JwtAuthTokenProvider(private val authConfigurationProperties: AuthConfigurationProperties) : AuthTokenProvider {
    private val log = LoggerFactory.getLogger(JwtAuthTokenProvider::class.java)

    val secret: ByteArray by lazy {
        val jwtConfigurationProperties = authConfigurationProperties.jwt
        if (jwtConfigurationProperties.secret.isBlank()) {
            throw TokenInvalidException()
        }
        jwtConfigurationProperties.secret.toByteArray(Charsets.UTF_8)
    }

    val expiration: Long by lazy {
        val jwtConfigurationProperties = authConfigurationProperties.jwt
        if (jwtConfigurationProperties.expired <= 0) {
            throw TokenInvalidException()
        }
        jwtConfigurationProperties.expired * 1000 // 转换为毫秒
    }

    /**
     * 创建一个 JWT。
     *
     * @return 签名后的 JWT 对象
     * @throws JOSEException 如果签名过程中出现错误
     */
    override fun createToken(authentication: SysUserAuthentication): String {
        // Header
        val header = JWSHeader(JWSAlgorithm.HS256)

        // Payload
        val claimsSet =
            JWTClaimsSet.Builder()
                .subject(authentication.name)
                .claim("id", authentication.id)
                .claim("username", authentication.name)
                .claim(
                    "roles",
                    authentication.authorities
                        .filter { it.authority.startsWith(Const.SECURITY_ROLE_PREFIX) }
                        .map { it.authority }
                        .joinToString(",") { it.replaceFirst(Const.SECURITY_ROLE_PREFIX, "") },
                )
                .claim(
                    "permissions",
                    authentication.authorities
                        .filter { !it.authority.startsWith(Const.SECURITY_ROLE_PREFIX) }
                        .joinToString(",") { it.authority },
                )
                .claim("departmentId", authentication.departmentId)
                .claim("dataScope", authentication.dataScope?.name)
                .expirationTime(Date(System.currentTimeMillis() + expiration)) // 1 hour from now
                .build()

        val signedJWT = SignedJWT(header, claimsSet)
        val signer = MACSigner(secret)
        signedJWT.sign(signer)
        return signedJWT.serialize()
    }

    /**
     * 解析一个 JWT 字符串。
     *
     * @param token JWT 字符串
     * @return 解析后的 SignedJWT 对象
     */
    private fun parseToken(token: String): SignedJWT {
        try {
            return SignedJWT.parse(token)
        } catch (e: Exception) {
            throw InvalidTokenException()
        }
    }

    /**
     * 验证 JWT 是否有效。
     *
     * @param token 签名后的 JWT 对象
     * @return 如果 JWT 有效则返回 true，否则返回 false
     */
    override fun validateToken(token: String): Boolean {
        val jwt = parseToken(token)
        val verifier = MACVerifier(secret)
        if (!jwt.verify(verifier)) {
            return false
        }
        val claimsSet = jwt.jwtClaimsSet
        return !claimsSet.expirationTime.before(Date())
    }

    override fun getAuthentication(token: String): Authentication {
        val jwt: SignedJWT = parseToken(token)
        val jwtClaimsSet = jwt.jwtClaimsSet ?: throw TokenInvalidException()
        val permissions = jwtClaimsSet.getStringClaim("permissions").split(",").filter { it.isNotBlank() }
        val roles =
            jwtClaimsSet
                .getStringClaim("roles")
                .split(",")
                .map { Const.SECURITY_ROLE_PREFIX.plus(it) }
                .filter { it.isNotBlank() }
        // 构建权限
        val authorities = mutableListOf(permissions, roles).flatten().map { SimpleGrantedAuthority(it) }

        val id = jwtClaimsSet.getClaim("id") as Long
        val username = jwtClaimsSet.getClaim("username") as String
        val departmentId = jwtClaimsSet.getClaim("departmentId") as Long?
        val dataScope = jwtClaimsSet.getClaim("dataScope") as String?
        return SysUserAuthentication(
            id = id,
            username = username,
            password = "",
            departmentId = departmentId,
            authorities = authorities,
            dataScope = dataScope?.let { DataScope.value(dataScope) },
        )
    }
}
