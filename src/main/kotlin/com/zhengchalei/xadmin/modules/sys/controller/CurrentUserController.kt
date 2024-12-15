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
package com.zhengchalei.xadmin.modules.sys.controller

import com.zhengchalei.xadmin.commons.R
import com.zhengchalei.xadmin.config.security.provider.AuthTokenProvider
import com.zhengchalei.xadmin.modules.sys.domain.SysUser
import com.zhengchalei.xadmin.modules.sys.service.SysUserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/current-user")
class CurrentUserController(
    private val authTokenProvider: AuthTokenProvider,
    private val sysUserService: SysUserService,
) {

    @GetMapping("/info")
    fun currentUser(): SysUser {
        return this.sysUserService.currentUserInfo()
    }

    @GetMapping("/logout")
    fun logout(): R<Boolean> {
        this.authTokenProvider.logout()
        return R.success(data = true)
    }
}
