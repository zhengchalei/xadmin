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
package com.zhengchalei.xadmin.modules.sys.domain.dto

import jakarta.validation.constraints.NotBlank

data class RegisterDTO(
    @NotBlank(message = "用户名不能为空") val username: String,
    @NotBlank(message = "密码不能为空") val password: String,
    @NotBlank(message = "邮箱不能为空") val email: String,
    @NotBlank(message = "验证码不能为空") val captcha: String,
)
