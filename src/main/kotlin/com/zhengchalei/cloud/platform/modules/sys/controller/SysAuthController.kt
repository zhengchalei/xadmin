package com.zhengchalei.cloud.platform.modules.sys.controller

import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.commons.R
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.LoginDTO
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.LoginResponse
import com.zhengchalei.cloud.platform.modules.sys.service.SysAuthService
import jakarta.servlet.http.HttpServletRequest
import org.babyfish.jimmer.client.meta.Api
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@Api("sys")
@Validated
@RestController
@RequestMapping("/api/auth")
class SysAuthController(
    private val sysAuthService: SysAuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO, httpServletRequest: HttpServletRequest): R<LoginResponse> {
        val ip = httpServletRequest.remoteAddr
        val token = sysAuthService.login(loginDTO.username, loginDTO.password, loginDTO.captcha, loginDTO.tenant, ip)
        return R(
            data = LoginResponse(accessToken = token, refreshToken = token, username = loginDTO.username),
        )
    }

    @PostMapping("/logout")
    fun logout() {

    }

    @PostMapping("/register")
    fun register() {

    }

    // 至于 SUPER_ADMIN 可以切换租户
    @PostMapping("/switch-tenant/{tenant}")
    fun switchTenant(@PathVariable tenant: String): R<LoginResponse> {
        val token = sysAuthService.switchTenant(tenant)
        return R(
            data = LoginResponse(accessToken = token, refreshToken = token, username = Const.SuperAdmin),
        )
    }
}