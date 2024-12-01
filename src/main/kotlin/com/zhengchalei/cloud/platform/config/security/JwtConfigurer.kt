/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.config.security

import com.zhengchalei.cloud.platform.config.security.provider.AuthTokenProvider
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver

class JwtConfigurer(
    private val authTokenProvider: AuthTokenProvider,
    private val handlerExceptionResolver: HandlerExceptionResolver,
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    override fun configure(http: HttpSecurity) {
        // 实例化 JwtFilter 拦截器, 将Token util bean 传递过来
        val customFilter = JwtAuthorizationFilter(authTokenProvider, handlerExceptionResolver)
        // 将这个 jwt filter 配置在 UsernamePasswordAuthenticationFilter.class 之前
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

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
}
