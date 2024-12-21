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
package com.zhengchalei.xadmin.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.zhengchalei.xadmin.commons.Const
import com.zhengchalei.xadmin.config.exceptions.GlobalException
import com.zhengchalei.xadmin.config.security.authentication.AuthenticationProvider
import com.zhengchalei.xadmin.config.security.filter.JwtAuthorizationFilter
import com.zhengchalei.xadmin.config.security.filter.StatefulTokenAuthorizationFilter
import com.zhengchalei.xadmin.config.security.provider.AuthConfigurationProperties
import com.zhengchalei.xadmin.config.security.provider.AuthTokenProvider
import com.zhengchalei.xadmin.config.security.provider.AuthTokenType
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Service
import org.springframework.web.servlet.HandlerExceptionResolver

@Service
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
class SpringSecurityConfig(
    @Value("\${spring.profiles.active}") private val profile: String,
    private val authConfigurationProperties: AuthConfigurationProperties,
    private val objectMapper: ObjectMapper,
    private val authenticationProvider: AuthenticationProvider,
    private val handlerExceptionResolver: HandlerExceptionResolver,
) {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        authTokenProvider: AuthTokenProvider,
        authenticationManager: AuthenticationManager,
    ): SecurityFilterChain {
        val authTokenFilter =
            when (authConfigurationProperties.tokenType) {
                AuthTokenType.JWT -> JwtAuthorizationFilter(authTokenProvider, handlerExceptionResolver)
                AuthTokenType.Stateful -> StatefulTokenAuthorizationFilter(authTokenProvider, handlerExceptionResolver)
            }

        return http
            .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .authenticationProvider(authenticationProvider)
            .authorizeHttpRequests { authorize ->

                // favicon.ico
                authorize.requestMatchers("/favicon.ico").permitAll()

                authorize.requestMatchers("/login.html", "/login").permitAll()
                authorize.requestMatchers("/register.html", "/register").permitAll()
                authorize.requestMatchers("/rest-password.html", "/rest-password").permitAll()
                authorize.requestMatchers("/api/auth/**").permitAll()
                // dev
                if (profile != Const.ENV_PROD) {
                    authorize.requestMatchers("/doc.html").permitAll()
                    authorize.requestMatchers("/webjars/**").permitAll()
                    authorize.requestMatchers("/openapi.html").permitAll()
                    authorize.requestMatchers("/openapi.yml").permitAll()
                    authorize.requestMatchers("/swagger-ui/**").permitAll()
                    authorize.requestMatchers("/v3/**").permitAll()
                }
                // 图片资源忽略鉴权
                authorize.requestMatchers("/api/external/file/download/**").permitAll()

                authorize.anyRequest().authenticated()
            }
            .exceptionHandling {
                it.authenticationEntryPoint { request, response, authException ->
                    // 判断 request 的请求方式, 如果是 get 并且判断是浏览器访问
                    if (shouldRedirectToLogin(request, response)) return@authenticationEntryPoint
                    response.sendError(
                        401,
                        objectMapper.writeValueAsString(GlobalException.Error(authException.message ?: "未登录")),
                    )
                }
                it.accessDeniedHandler { request, response, accessDeniedException ->
                    if (shouldRedirectToLogin(request, response)) return@accessDeniedHandler
                    response.sendError(
                        403,
                        objectMapper.writeValueAsString(GlobalException.Error(accessDeniedException.message ?: "无权限")),
                    )
                }
            }
            .sessionManagement { it.disable() }
            .csrf { it.disable() }
            .build()
    }

    private fun shouldRedirectToLogin(request: HttpServletRequest, response: HttpServletResponse): Boolean {
        if (request.method == "GET" && request.getHeader("User-Agent")?.contains("Mozilla") == true) {
            // 重定向到登录页面
            response.sendRedirect("/login.html")
            return true
        }
        return false
    }

    @Bean
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager =
        configuration.authenticationManager
}
