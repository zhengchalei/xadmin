/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.controller

import com.zhengchalei.cloud.platform.commons.R
import com.zhengchalei.cloud.platform.config.IPUtil.getIpAddress
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.LoginDTO
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.LoginResponse
import com.zhengchalei.cloud.platform.modules.sys.service.SysAuthService
import jakarta.servlet.http.HttpServletRequest
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
                ip,
            )
        return R(data = LoginResponse(accessToken = token, refreshToken = token, username = loginDTO.username))
    }

    @PostMapping("/logout")
    fun logout(): R<Boolean> {
        this.sysAuthService.logout()
        return R.success(data = true)
    }
}
