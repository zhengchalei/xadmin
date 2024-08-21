package com.zhengchalei.cloud.platform.config.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class TenantCaptchaAuthenticationToken(
    username: Any,
    password: Any,
    val tenant: String,
    val captcha: String,
    authorities: Collection<GrantedAuthority> = emptyList()
) :
    UsernamePasswordAuthenticationToken(username, password, authorities)
