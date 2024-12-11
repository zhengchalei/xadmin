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
package com.zhengchalei.xadmin.config.security.filter

import com.zhengchalei.xadmin.config.security.provider.AuthTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver

class JwtAuthorizationFilter(
    private val authTokenProvider: AuthTokenProvider,
    private val handlerExceptionResolver: HandlerExceptionResolver,
) : OncePerRequestFilter() {
    private val AUTHORIZATION: String = "Authorization"
    private val ACCESS_TOKEN: String = "access_token"

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        // 这里就是获取到token
        try {
            val jwt: String? = resolveToken(request)
            // 如果 jwt不为空 然后调用了 jwtUtil 效验了 token 是否有效
            if (!jwt.isNullOrBlank() && authTokenProvider.validateToken(jwt)) {
                // 获取 Authentication
                val authentication: Authentication = authTokenProvider.getAuthentication(jwt)
                // 将 认证信息重新set 到 security context 中
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            handlerExceptionResolver.resolveException(request, response, null, e)
            return
        }
        // 拦截器继续执行,
        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        // 从头部信息拿到 Authorization 的内容
        val bearerToken = request.getHeader(AUTHORIZATION)
        // 如果 不为空, 且 Bearer 开头
        if (!bearerToken.isNullOrBlank() && bearerToken.startsWith("Bearer ")) {
            // 这里的 7 长度就是 "Bearer " 的长度
            return bearerToken.substring(7)
        }

        val accessToken = request.getParameter(ACCESS_TOKEN)
        if (!accessToken.isNullOrBlank()) {
            return accessToken
        }
        return null
    }
}
