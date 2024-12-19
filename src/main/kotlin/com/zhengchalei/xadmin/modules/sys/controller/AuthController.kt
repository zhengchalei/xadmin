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
package com.zhengchalei.xadmin.modules.sys.controller

import com.zhengchalei.xadmin.commons.Const
import com.zhengchalei.xadmin.commons.R
import com.zhengchalei.xadmin.commons.util.IPUtil.getIpAddress
import com.zhengchalei.xadmin.modules.sys.domain.dto.LoginDTO
import com.zhengchalei.xadmin.modules.sys.domain.dto.LoginResponse
import com.zhengchalei.xadmin.modules.sys.domain.dto.RegisterDTO
import com.zhengchalei.xadmin.modules.sys.domain.dto.RestPasswordDTO
import com.zhengchalei.xadmin.modules.sys.service.AuthService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.util.*
import net.dreamlu.mica.captcha.service.ICaptchaService
import org.babyfish.jimmer.client.meta.Api
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ByteArrayResource
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
    private val captchaService: ICaptchaService,
    @Value("\${spring.profiles.active}") private val profile: String,
) {
    private val log = LoggerFactory.getLogger(AuthController::class.java)

    @PostMapping("/login")
    fun login(
        @RequestBody loginDTO: LoginDTO,
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
    ): R<LoginResponse> {
        val ip = getIpAddress(httpServletRequest)
        val token = authService.login(loginDTO.username, loginDTO.password, loginDTO.captcha, loginDTO.captchaID, ip)
        httpServletResponse.addCookie(
            Cookie(Const.TOKEN_HEADER, token).apply {
                path = "/" // 设为全局路径
                isHttpOnly = true // 防止 JavaScript 访问
                secure = httpServletRequest.isSecure // 根据请求协议动态设置
                maxAge = -1 // 设置有效期（秒）
            }
        )
        return R(data = LoginResponse(accessToken = token, refreshToken = token, username = loginDTO.username))
    }

    @GetMapping("/captcha")
    fun captcha(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse) {
        val uuid = UUID.randomUUID().toString()
        val byteArrayResource: ByteArrayResource = captchaService.generateByteResource(uuid)
        httpServletResponse.contentType = "image/png"
        httpServletResponse.setContentLength(byteArrayResource.byteArray.size)
        httpServletResponse.setHeader("Cache-Control", "no-store, no-cache")
        httpServletResponse.setHeader("Pragma", "no-cache")
        httpServletResponse.setHeader("captchaID", uuid)
        httpServletResponse.setDateHeader("Expires", 0)
        httpServletResponse.outputStream.write(byteArrayResource.byteArray)
        httpServletResponse.flushBuffer()
        return
    }

    @GetMapping("/captcha/{uuid}")
    fun captcha(
        @PathVariable uuid: String,
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
    ) {
        val byteArrayResource: ByteArrayResource = captchaService.generateByteResource(uuid)
        httpServletResponse.contentType = "image/png"
        httpServletResponse.setContentLength(byteArrayResource.byteArray.size)
        httpServletResponse.setHeader("Cache-Control", "no-store, no-cache")
        httpServletResponse.setHeader("Pragma", "no-cache")
        httpServletResponse.setHeader("captchaID", uuid)
        httpServletResponse.setDateHeader("Expires", 0)
        httpServletResponse.outputStream.write(byteArrayResource.byteArray)
        httpServletResponse.flushBuffer()
        return
    }

    @PostMapping("/send-register-email-code/{email}")
    fun sendRegisterEmailCode(@PathVariable email: String): R<Unit> {
        this.authService.sendRegisterEmailCode(email)
        return R()
    }

    @PostMapping("/register")
    fun register(@RequestBody registerDTO: RegisterDTO): R<Unit> {
        this.authService.register(registerDTO)
        return R()
    }

    // 找回密码
    @PostMapping("/send-rest-password-email-code/{email}")
    fun sendRestPasswordEmailCode(@PathVariable email: String): R<Unit> {
        this.authService.sendRestPasswordEmailCode(email)
        return R()
    }

    @PostMapping("/rest-password")
    fun restPassword(@RequestBody restPasswordDTO: RestPasswordDTO): R<Unit> {
        this.authService.restPassword(restPasswordDTO)
        return R()
    }
}
