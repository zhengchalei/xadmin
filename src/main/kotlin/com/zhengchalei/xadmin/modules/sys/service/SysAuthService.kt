/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.sys.service

import com.zhengchalei.xadmin.config.exceptions.LoginFailException
import com.zhengchalei.xadmin.config.exceptions.ServiceException
import com.zhengchalei.xadmin.config.virtualThread.VirtualThreadExecutor
import com.zhengchalei.xadmin.config.security.SysUserAuthentication
import com.zhengchalei.xadmin.config.security.provider.AuthTokenProvider
import com.zhengchalei.xadmin.modules.sys.domain.SysLoginLog
import com.zhengchalei.xadmin.modules.sys.domain.SysUser
import com.zhengchalei.xadmin.modules.sys.domain.by
import com.zhengchalei.xadmin.modules.sys.repository.SysLoginLogRepository
import java.time.LocalDateTime
import net.dreamlu.mica.captcha.service.ICaptchaService
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher
import org.babyfish.jimmer.kt.makeIdOnly
import org.babyfish.jimmer.kt.new
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

/**
 * 系统授权服务
 *
 * @param [sysLoginLogRepository] 系统登录日志存储库
 * @param [authenticationManager] 认证管理器
 * @param [authTokenProvider] jwt 提供商
 * @constructor 创建[SysAuthService]
 * @author 郑查磊
 * @date 2024-08-17
 */
@Service
class SysAuthService(
    private val sysLoginLogRepository: SysLoginLogRepository,
    private val authenticationManager: AuthenticationManager,
    private val authTokenProvider: AuthTokenProvider,
    private val userService: SysUserService,
    private val ip2regionSearcher: Ip2regionSearcher,
    private val virtualThreadExecutor: VirtualThreadExecutor,
    private val captchaService: ICaptchaService,
) {
    private val log = LoggerFactory.getLogger(SysAuthService::class.java)

    /**
     * 登录
     *
     * @param [username] 用户名
     * @param [password] 密码
     * @param [captcha] 验证码
     * @param [captchaID] 验证码ID
     * @param [ip] ip
     * @return [String]
     */
    fun login(username: String, password: String, captcha: String, captchaID: String, ip: String): String {
        try {
            log.info("登录: username: {}, ip: {}, captcha: {}", username, ip, captcha)
            // 验证码验证
            if (this.captchaService.validate(captchaID, captcha).not()) {
                throw LoginFailException("验证码错误")
            }
            val authentication: SysUserAuthentication =
                authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
                    as SysUserAuthentication
            SecurityContextHolder.getContext().authentication = authentication
            log.info("登录成功, username {} ", username)
            val token: String = authTokenProvider.createToken(authentication)
            return token
        } catch (e: ServiceException) {
            log.error("登录失败", e)
            saveLoginLog(username, password, false, e.message, ip)
            throw e
        } catch (e: Exception) {
            log.error("登录失败", e)
            saveLoginLog(username, password, false, e.message, ip)
            throw LoginFailException()
        } finally {
            saveLoginLog(username, "", true, null, ip)
        }
    }

    /**
     * 保存登录日志
     *
     * @param [username] 用户名
     * @param [password] 密码
     * @param [status] 地位
     * @param [errorMessage] 错误信息
     * @param [ip] ip
     */
    private fun saveLoginLog(username: String, password: String, status: Boolean, errorMessage: String?, ip: String) {
        this.virtualThreadExecutor.submit {
            val address = this.ip2regionSearcher.getAddress(ip)
            log.info(
                "保存登录日志: username: {}, ip: {}, address: {}, status: {}, errorMessage: {}",
                username,
                ip,
                address,
                status,
                errorMessage,
            )
            this.sysLoginLogRepository.insert(
                new(SysLoginLog::class).by {
                    this.username = username
                    this.password = password
                    this.status = status
                    this.errorMessage = errorMessage
                    this.loginTime = LocalDateTime.now()
                    this.ip = ip
                    this.address = address
                    this.user = if (status) makeIdOnly(SysUser::class, userService.currentUserId()) else null
                }
            )
        }
    }

    fun logout() {
        // TODO 期望值， 如果有Redis,使用Redis 存储JWT -》 User
        // 否则为 DB PG 存储
    }
}
