package com.zhengchalei.cloud.platform.modules.sys.domain.dto

data class LoginResponse(var username: String, var accessToken: String, val refreshToken: String)
