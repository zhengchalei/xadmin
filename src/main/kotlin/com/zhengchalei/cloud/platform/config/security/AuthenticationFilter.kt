/// *
// * 版权所有 © 2024 郑查磊.
// * 保留所有权利.
// *
// * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
// */
// package com.zhengchalei.cloud.platform.config.security
//
// import jakarta.servlet.http.HttpServletRequest
// import jakarta.servlet.http.HttpServletResponse
// import org.springframework.security.core.Authentication
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
//
// class AuthenticationFilter : UsernamePasswordAuthenticationFilter() {
//    override fun attemptAuthentication(request: HttpServletRequest, response:
// HttpServletResponse): Authentication {
//        val username = request.getParameter("username")
//        val password = request.getParameter("password")
//        val authRequest = SysUserAuthentication(
//            username = username,
//            password = password,
//        )
//        super.setDetails(request, authRequest)
//        return authenticationManager.authenticate(authRequest)
//    }
// }
