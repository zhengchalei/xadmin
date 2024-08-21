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
import com.zhengchalei.cloud.platform.modules.sys.repository.SysDictRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtProvider(private val dictRepository: SysDictRepository) : CommandLineRunner {

    private val logger = LoggerFactory.getLogger(JwtProvider::class.java)

    private var secret: ByteArray = "nnaWuft6pSSKVkcuzlmqBWi3vO4Cin44".toByteArray(Charsets.UTF_8)

    private var expiration: Long = 3600L

    override fun run(vararg args: String) {
        val dict = this.dictRepository.findByCode("jwt")
        val dictItems = dict?.dictItems ?: emptyList()
        // 获取 secret
        val secret = dictItems.find { it.code == "secret" }
        // 获取 expired
        val expired = dictItems.find { it.code == "expired" }

        when {
            secret == null -> {
                logger.error("JWT 参数初始化失败, 缺少 'secret', 正在使用默认值: 危险")
                this.secret = "default-secret".toByteArray(Charsets.UTF_8)
            }

            secret.data.toByteArray(Charsets.UTF_8).size < 32 -> {
                logger.error("JWT 参数错误, 'secret' 必须大于 32 个字符, 正在使用默认值: 危险")
            }

            else -> {
                this.secret = secret.data.toByteArray(Charsets.UTF_8)
            }
        }

        when {
            expired == null -> {
                logger.warn("JWT 参数初始化失败, 缺少 'expired'")
                this.expiration = 3600L // 默认过期时间为 1 小时
            }

            expired.data.toLong() < 0 -> {
                logger.warn("JWT 参数初始化错误, 'expired' 必须大于 0 , 正在使用默认值: 危险")
            }

            else -> {
                this.expiration = expired.data.toLong() * 1000 // 转换为毫秒
            }
        }

        logger.info("jwt 参数初始化完毕")
    }

    /**
     * 创建一个 JWT。
     *
     * @return 签名后的 JWT 对象
     * @throws JOSEException 如果签名过程中出现错误
     */
    fun createAccessToken(authentication: TenantCaptchaAuthenticationToken): String {
        // Header
        val header = JWSHeader(JWSAlgorithm.HS256)

        // Payload
        val claimsSet = JWTClaimsSet.Builder()
            .subject(authentication.name)
            .claim("username", authentication.name)
            .claim(
                "roles",
                authentication.authorities
                    .filter { it.authority.startsWith(Const.SecurityRolePrifix) }
                    .map { it.authority }
                    .map { it.replaceFirst(Const.SecurityRolePrifix, "") }
                    .joinToString(",")
            )
            .claim(
                "permissions",
                authentication.authorities
                    .filter { !it.authority.startsWith(Const.SecurityRolePrifix) }
                    .map { it.authority }
                    .joinToString(",")
            )
            .claim("tenant", authentication.tenant)
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
        val roles = jwtClaimsSet.getStringClaim("roles").split(",").map { Const.SecurityRolePrifix.plus(it) }.filter { it.isNotBlank() }
        val tenant = jwtClaimsSet.getStringClaim("tenant")

        // 构建权限
        val authorities = mutableListOf(permissions, roles).flatten().map { SimpleGrantedAuthority(it) }

        val principal = User(jwtClaimsSet.subject, "", authorities)

        return TenantCaptchaAuthenticationToken(
            username = principal,
            password = "",
            tenant = tenant,
            captcha = "",
            authorities = authorities
        )
    }

    private fun getListClaimsSet(jwtClaimsSet: JWTClaimsSet, key: String): List<String> {
        return (jwtClaimsSet.getClaim(key) as List<*>).map { it as String }
    }

}