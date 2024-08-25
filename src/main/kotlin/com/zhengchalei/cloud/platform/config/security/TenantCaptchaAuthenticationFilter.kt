package com.zhengchalei.cloud.platform.config.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class TenantCaptchaAuthenticationFilter : UsernamePasswordAuthenticationFilter() {
    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
    ): Authentication {
        val username = request.getParameter("username")
        val password = request.getParameter("password")
        val tenantId = request.getParameter("tenant")
        val captcha = request.getParameter("captcha")

        val authRequest =
            TenantCaptchaAuthenticationToken(
                username,
                password,
                tenantId,
                captcha,
            )
        super.setDetails(request, authRequest)
        return authenticationManager.authenticate(authRequest)
    }
}
