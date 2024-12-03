/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.config.security

import com.zhengchalei.cloud.platform.config.security.filter.JwtAuthorizationFilter
import com.zhengchalei.cloud.platform.config.security.provider.AuthTokenProvider
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.HandlerExceptionResolver

class TokenFilter(
    private val authTokenProvider: AuthTokenProvider,
    private val handlerExceptionResolver: HandlerExceptionResolver,
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
    override fun configure(http: HttpSecurity) {
        // 实例化 JwtFilter 拦截器, 将Token util bean 传递过来
        val customFilter = JwtAuthorizationFilter(authTokenProvider, handlerExceptionResolver)
        // 将这个 jwt filter 配置在 UsernamePasswordAuthenticationFilter.class 之前
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}
