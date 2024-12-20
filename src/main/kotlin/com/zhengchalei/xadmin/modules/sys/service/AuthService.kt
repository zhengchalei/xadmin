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
package com.zhengchalei.xadmin.modules.sys.service

import com.zhengchalei.xadmin.commons.Const
import com.zhengchalei.xadmin.config.email.EmailService
import com.zhengchalei.xadmin.config.exceptions.LoginFailException
import com.zhengchalei.xadmin.config.exceptions.ServiceException
import com.zhengchalei.xadmin.config.security.authentication.SysUserAuthentication
import com.zhengchalei.xadmin.config.security.provider.AuthTokenProvider
import com.zhengchalei.xadmin.config.virtualThread.VirtualThreadExecutor
import com.zhengchalei.xadmin.modules.sys.domain.SysLoginLog
import com.zhengchalei.xadmin.modules.sys.domain.by
import com.zhengchalei.xadmin.modules.sys.domain.dto.RegisterDTO
import com.zhengchalei.xadmin.modules.sys.domain.dto.RestPasswordDTO
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysUserCreateInput
import com.zhengchalei.xadmin.modules.sys.repository.SysLoginLogRepository
import java.time.LocalDateTime
import kotlin.random.Random
import net.dreamlu.mica.captcha.service.ICaptchaService
import net.dreamlu.mica.ip2region.core.Ip2regionSearcher
import org.babyfish.jimmer.kt.new
import org.redisson.api.RedissonClient
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
 * @constructor 创建[AuthService]
 * @author 郑查磊
 * @date 2024-08-17
 */
@Service
class AuthService(
    private val sysLoginLogRepository: SysLoginLogRepository,
    private val authenticationManager: AuthenticationManager,
    private val authTokenProvider: AuthTokenProvider,
    private val ip2regionSearcher: Ip2regionSearcher,
    private val virtualThreadExecutor: VirtualThreadExecutor,
    private val captchaService: ICaptchaService,
    private val sysUserService: SysUserService,
    private val emailService: EmailService,
    private val redissonClient: RedissonClient,
) {
    private val log = LoggerFactory.getLogger(AuthService::class.java)

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
                }
            )
        }
    }

    fun logout() {
        // TODO 期望值， 如果有Redis,使用Redis 存储JWT -》 User
        this.authTokenProvider.logout()
    }

    fun register(registerDTO: RegisterDTO) {
        // 判断验证码
        val registerMap = this.redissonClient.getMap<String, String>(Const.REGISTER_KEY)
        val captchaCode = registerMap[registerDTO.email] ?: throw LoginFailException("邮箱未注册")
        registerMap.remove(registerDTO.email)
        if (captchaCode != registerDTO.captcha) {
            throw LoginFailException("验证码错误")
        }
        val userDetailView =
            this.sysUserService.createSysUser(
                SysUserCreateInput.Builder()
                    .username(registerDTO.username)
                    .email(registerDTO.email)
                    .status(false)
                    .roleIds(listOf())
                    .build()
            )
        this.sysUserService.changePassword(userDetailView.id, registerDTO.password)
    }

    fun sendRegisterEmailCode(email: String) {
        val captchaCode = Random.nextInt(1000, 10000).toString()
        val registerMap = this.redissonClient.getMap<String, String>(Const.REGISTER_KEY)
        if (registerMap.containsKey(email)) {
            throw LoginFailException("验证码已发送， 请检查邮箱！")
        }
        registerMap[email] = captchaCode
        this.emailService.sendVerificationCode(email, captchaCode)
        log.info("发送注册验证码: email: {}, code: {}", email, captchaCode)
    }

    fun sendRestPasswordEmailCode(email: String) {
        val restPasswordMap = this.redissonClient.getMap<String, String>(Const.REGISTER_KEY)
        val captchaCode = Random.nextInt(1000, 10000).toString()
        restPasswordMap[email] = captchaCode
        this.emailService.sendVerificationCode(email, captchaCode)
        log.info("发送找回密码验证码: email: {}, code: {}", email, captchaCode)
    }

    fun restPassword(restPasswordDTO: RestPasswordDTO) {
        val restPasswordMap = this.redissonClient.getMap<String, String>(Const.REGISTER_KEY)
        val captchaCode = restPasswordMap[restPasswordDTO.email] ?: throw LoginFailException("验证码不存在")
        restPasswordMap.remove(restPasswordDTO.email)
        if (captchaCode != restPasswordDTO.captcha) {
            throw LoginFailException("验证码错误")
        }
        val user = this.sysUserService.findSysUserByEmail(restPasswordDTO.email)
        this.sysUserService.changePassword(user.id, restPasswordDTO.newPassword)
    }
}
