/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.controller

import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.commons.R
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.LoginDTO
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.LoginResponse
import com.zhengchalei.cloud.platform.modules.sys.service.SysAuthService
import jakarta.servlet.http.HttpServletRequest
import java.net.InetAddress
import org.babyfish.jimmer.client.meta.Api
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/auth")
class SysAuthController(
    private val sysAuthService: SysAuthService,
    @Value("\${spring.profiles.active}") private val profile: String,
) {
    private val log = LoggerFactory.getLogger(SysAuthController::class.java)

    @PostMapping("/login")
    fun login(
        @RequestBody loginDTO: LoginDTO,
        httpServletRequest: HttpServletRequest,
    ): R<LoginResponse> {
        val ip = getIpAddress(httpServletRequest)
        val token =
            sysAuthService.login(
                loginDTO.username,
                loginDTO.password,
                loginDTO.captcha,
                loginDTO.tenant,
                ip,
            )
        return R(
            data =
                LoginResponse(
                    accessToken = token,
                    refreshToken = token,
                    username = loginDTO.username,
                ))
    }

    fun getIpAddress(request: HttpServletRequest): String {
        try {
            var ipAddress = request.getHeader("x-forwarded-for")
            if (ipAddress.isNullOrEmpty() || "unknown".equals(ipAddress, ignoreCase = true)) {
                ipAddress = request.getHeader("Proxy-Client-IP")
            }
            if (ipAddress.isNullOrEmpty() || "unknown".equals(ipAddress, ignoreCase = true)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP")
            }
            if (ipAddress.isNullOrEmpty() || "unknown".equals(ipAddress, ignoreCase = true)) {
                ipAddress = request.remoteAddr
                if (ipAddress == "0:0:0:0:0:0:0:1") {
                    // 根据网卡取本机配置的IP
                    ipAddress =
                        if (profile == Const.ENV_PROD) InetAddress.getLocalHost().hostAddress
                        else "127.0.0.1"
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照','分割
            if (ipAddress.isNotEmpty() && ipAddress.length > 15 && ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","))
            }
            return ipAddress
        } catch (e: Exception) {
            log.error("获取ip地址失败", e)
        }
        return ""
    }

    @PostMapping("/logout")
    fun logout(): R<Boolean> {
        this.sysAuthService.logout()
        return R.success(data = true)
    }

    // 至于 SUPER_ADMIN 可以切换租户
    @PostMapping("/switch-tenant/{tenant}")
    fun switchTenant(@PathVariable tenant: String): R<LoginResponse> {
        val token = sysAuthService.switchTenant(tenant)
        return R(
            data = LoginResponse(accessToken = token, refreshToken = token, username = Const.Root))
    }
}
