package com.zhengchalei.cloud.platform.config.security

import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jose.crypto.MACVerifier
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.config.InvalidTokenException
import com.zhengchalei.cloud.platform.config.properties.AuthConfigurationProperties
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtProvider(
    private val authConfigurationProperties: AuthConfigurationProperties,
) : CommandLineRunner {
    private val log = LoggerFactory.getLogger(JwtProvider::class.java)

    private var secret: ByteArray = "".toByteArray(Charsets.UTF_8)

    private var expiration: Long = 3600L

    override fun run(vararg args: String) {
        val jwtConfigurationProperties = authConfigurationProperties.jwt
        if (jwtConfigurationProperties.secret.isBlank()) {
            throw RuntimeException("JWT secret is empty")
        }
        if (jwtConfigurationProperties.expired <= 0) {
            throw RuntimeException("JWT expired must be greater than 0")
        }
        // 获取 secret
        this.secret = jwtConfigurationProperties.secret.toByteArray(Charsets.UTF_8)
        // 获取 expired
        this.expiration = jwtConfigurationProperties.expired * 1000 // 转换为毫秒
    }

    /**
     * 创建一个 JWT。
     *
     * @return 签名后的 JWT 对象
     * @throws JOSEException 如果签名过程中出现错误
     */
    fun createAccessToken(authentication: TenantAuthenticationToken): String {
        // Header
        val header = JWSHeader(JWSAlgorithm.HS256)

        // Payload
        val claimsSet =
            JWTClaimsSet
                .Builder()
                .subject(authentication.name)
                .claim("username", authentication.name)
                .claim(
                    "roles",
                    authentication.authorities
                        .filter { it.authority.startsWith(Const.SecurityRolePrifix) }
                        .map { it.authority }
                        .map { it.replaceFirst(Const.SecurityRolePrifix, "") }
                        .joinToString(","),
                ).claim(
                    "permissions",
                    authentication.authorities
                        .filter { !it.authority.startsWith(Const.SecurityRolePrifix) }
                        .map { it.authority }
                        .joinToString(","),
                ).claim("tenant", authentication.tenant)
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
    fun parseToken(token: String): SignedJWT {
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
    fun validateToken(token: String): Boolean {
        val jwt = parseToken(token)
        val verifier = MACVerifier(secret)
        if (!jwt.verify(verifier)) {
            return false
        }
        val claimsSet = jwt.jwtClaimsSet
        return !claimsSet.expirationTime.before(Date())
    }

    fun getAuthentication(token: String): Authentication {
        val jwt: SignedJWT = parseToken(token)
        val jwtClaimsSet = jwt.jwtClaimsSet ?: throw RuntimeException("JWT ClaimsSet is null")
        val permissions = jwtClaimsSet.getStringClaim("permissions").split(",").filter { it.isNotBlank() }
        val roles =
            jwtClaimsSet
                .getStringClaim("roles")
                .split(",")
                .map { Const.SecurityRolePrifix.plus(it) }
                .filter { it.isNotBlank() }
        val tenant = jwtClaimsSet.getStringClaim("tenant")

        // 构建权限
        val authorities = mutableListOf(permissions, roles).flatten().map { SimpleGrantedAuthority(it) }

        val principal = User(jwtClaimsSet.subject, "", authorities)

        return TenantAuthenticationToken(
            username = principal,
            password = "",
            tenant = tenant,
            captcha = "",
            authorities = authorities,
        )
    }

}
