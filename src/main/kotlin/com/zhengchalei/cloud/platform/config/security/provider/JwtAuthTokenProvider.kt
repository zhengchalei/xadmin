/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.config.security.provider

import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.config.InvalidTokenException
import com.zhengchalei.cloud.platform.config.TokenInvalidException
import com.zhengchalei.cloud.platform.config.security.SysUserAuthentication
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.*

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
                        .filter { it.authority.startsWith(Const.SecurityRolePrifix) }
                        .map { it.authority }
                        .map { it.replaceFirst(Const.SecurityRolePrifix, "") }
                        .joinToString(","),
                )
                .claim(
                    "permissions",
                    authentication.authorities
                        .filter { !it.authority.startsWith(Const.SecurityRolePrifix) }
                        .map { it.authority }
                        .joinToString(","),
                )
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
                .map { Const.SecurityRolePrifix.plus(it) }
                .filter { it.isNotBlank() }
        // 构建权限
        val authorities = mutableListOf(permissions, roles).flatten().map { SimpleGrantedAuthority(it) }

        val id = jwtClaimsSet.getClaim("id") as Long
        val username = jwtClaimsSet.getClaim("username") as String
        return SysUserAuthentication(
            id = id,
            username = username,
            password = "",
            authorities = authorities
        )
    }

    override fun logout(token: String) {
        // 什么也不做 , 因为 JWT 是无状态的
    }
}
