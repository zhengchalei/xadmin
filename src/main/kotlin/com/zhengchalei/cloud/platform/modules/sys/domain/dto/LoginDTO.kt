/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.domain.dto

import jakarta.validation.constraints.NotBlank

data class LoginDTO(
    @NotBlank(message = "用户名不能为空") val username: String,
    @NotBlank(message = "密码不能为空") val password: String,
    @NotBlank(message = "验证码不能为空") val captcha: String,
    @NotBlank(message = "验证码ID不能为空") val captchaID: String,
)
