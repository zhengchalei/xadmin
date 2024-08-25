package com.zhengchalei.cloud.platform.modules.sys.service

import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.config.IP2RegionService
import com.zhengchalei.cloud.platform.config.LoginFailException
import com.zhengchalei.cloud.platform.config.ServiceException
import com.zhengchalei.cloud.platform.config.SwitchTenantException
import com.zhengchalei.cloud.platform.config.security.JwtProvider
import com.zhengchalei.cloud.platform.config.security.TenantCaptchaAuthenticationToken
import com.zhengchalei.cloud.platform.modules.sys.domain.SysLoginLog
import com.zhengchalei.cloud.platform.modules.sys.domain.SysUser
import com.zhengchalei.cloud.platform.modules.sys.domain.by
import com.zhengchalei.cloud.platform.modules.sys.repository.SysLoginLogRepository
import org.babyfish.jimmer.kt.makeIdOnly
import org.babyfish.jimmer.kt.new
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import java.time.LocalDateTime


/**
 * 系统授权服务
 * @author 郑查磊
 * @date 2024-08-17
 * @constructor 创建[SysAuthService]
 * @param [sysLoginLogRepository] 系统登录日志存储库
 * @param [authenticationManager] 认证管理器
 * @param [jwtProvider] jwt 提供商
 */
@Service
class SysAuthService(
    val sysLoginLogRepository: SysLoginLogRepository,
    val authenticationManager: AuthenticationManager,
    val jwtProvider: JwtProvider,
    val userService: SysUserService,
    val ip2RegionService: IP2RegionService
) {

    private val log = LoggerFactory.getLogger(SysAuthService::class.java)

    /**
     * 登录
     * @param [username] 用户名
     * @param [password] 密码
     * @param [captcha] 验证码
     * @param [tenant] 租户
     * @param [ip] ip
     * @return [String]
     */
    fun login(username: String, password: String, captcha: String, tenant: String, ip: String): String {
        try {
            val authentication: TenantCaptchaAuthenticationToken = authenticationManager.authenticate(
                TenantCaptchaAuthenticationToken(
                    username = username,
                    password = password,
                    captcha = captcha,
                    tenant = tenant
                )
            ) as TenantCaptchaAuthenticationToken
            SecurityContextHolder.getContext().authentication = authentication

            saveLoginLog(username, "", true, null, ip, tenant)

            val token: String = jwtProvider.createAccessToken(authentication)
            return token
        } catch (e: ServiceException) {
            log.error("登录失败", e)
            saveLoginLog(username, password, false, e.message, ip, tenant)
            throw e
        } catch (e: Exception) {
            log.error("登录失败", e)
            saveLoginLog(username, password, false, e.message, ip, tenant)
            throw LoginFailException()
        }
    }

    /**
     * 切换租户
     * @param [tenant] 租户
     */
    fun switchTenant(tenant: String): String {
        // 判断用户名是否为 superAdmin
        if ((SecurityContextHolder.getContext().authentication.principal as User).username == Const.SuperAdmin) {
            // 切换租户
            SecurityContextHolder.getContext().authentication = TenantCaptchaAuthenticationToken(
                username = Const.SuperAdmin,
                password = "",
                captcha = "",
                tenant = tenant
            )
            val authentication: TenantCaptchaAuthenticationToken =
                SecurityContextHolder.getContext().authentication as TenantCaptchaAuthenticationToken
            val token: String = jwtProvider.createAccessToken(authentication)
            return token
        } else {
            throw SwitchTenantException()
        }
    }

    /**
     * 保存登录日志
     * @param [username] 用户名
     * @param [password] 密码
     * @param [status] 地位
     * @param [errorMessage] 错误信息
     * @param [ip] ip
     * @param [tenant] 租户
     */
    private fun saveLoginLog(
        username: String,
        password: String,
        status: Boolean,
        errorMessage: String?,
        ip: String,
        tenant: String,
    ) {
        val address = this.ip2RegionService.search(ip)
        this.sysLoginLogRepository.save(new(SysLoginLog::class).by {
            this.username = username
            this.password = password
            this.status = status
            this.errorMessage = errorMessage
            this.loginTime = LocalDateTime.now()
            this.ip = ip
            this.address = address
            this.tenant = tenant
            this.sysUser = if (status) makeIdOnly(SysUser::class, userService.currentUserInfo().id) else null
        })
    }
}