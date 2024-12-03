/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
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

class StatefulTokenAuthorizationFilter(
    private val authTokenProvider: AuthTokenProvider,
    private val handlerExceptionResolver: HandlerExceptionResolver,
) : OncePerRequestFilter() {

    private val TOKEN: String = "token"

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        // 这里就是获取到token
        try {
            val token: String? = resolveToken(request)
            // 如果 jwt不为空 然后调用了 jwtUtil 效验了 token 是否有效
            if (!token.isNullOrBlank() && authTokenProvider.validateToken(token)) {
                // 获取 Authentication
                val authentication: Authentication = authTokenProvider.getAuthentication(token)
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
        val token = request.getHeader(TOKEN)
        return token ?: request.getParameter(TOKEN)
    }
}
