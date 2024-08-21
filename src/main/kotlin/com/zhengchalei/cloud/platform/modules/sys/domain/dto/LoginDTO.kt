package com.zhengchalei.cloud.platform.modules.sys.domain.dto

import jakarta.validation.constraints.NotBlank

data class LoginDTO(

    @NotBlank(message = "用户名不能为空")
    val username: String,
    @NotBlank(message = "密码不能为空")
    val password: String,
    @NotBlank(message = "租户ID不能为空")
    val tenant: String,
    @NotBlank(message = "验证码不能为空")
    val captcha: String
)