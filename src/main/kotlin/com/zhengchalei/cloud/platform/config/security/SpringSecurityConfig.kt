/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.config.GlobalException
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
    private val objectMapper: ObjectMapper,
    private val tenantAuthenticationProvider: AuthenticationProvider,
    private val handlerExceptionResolver: HandlerExceptionResolver,
) {
    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtProvider: JwtProvider,
        authenticationManager: AuthenticationManager,
    ): SecurityFilterChain {
        val authenticationFilter = AuthenticationFilter()
        authenticationFilter.setAuthenticationManager(authenticationManager)
        return http
            .addFilterBefore(
                JwtConfigurer.JwtAuthorizationFilter(jwtProvider, handlerExceptionResolver),
                UsernamePasswordAuthenticationFilter::class.java,
            )
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .authenticationProvider(tenantAuthenticationProvider)
            .authorizeHttpRequests { authorize ->

                // favicon.ico
                authorize.requestMatchers("/favicon.ico").permitAll()

                authorize.requestMatchers("/api/auth/login").permitAll()
                authorize.requestMatchers("/api/auth/register").permitAll()
                authorize.requestMatchers("/api/auth/captcha").permitAll()
                // dev
                if (profile != Const.ENV_PROD) {
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
                it.authenticationEntryPoint { _, response, authException ->
                    response.sendError(
                        401,
                        objectMapper.writeValueAsString(GlobalException.Error(authException.message ?: "未登录")),
                    )
                }
                it.accessDeniedHandler { _, response, accessDeniedException ->
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

    @Bean
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager =
        configuration.authenticationManager
}
