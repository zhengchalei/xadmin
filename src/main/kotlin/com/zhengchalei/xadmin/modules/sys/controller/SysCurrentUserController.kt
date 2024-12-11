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
import com.zhengchalei.xadmin.modules.sys.domain.SysUser
import com.zhengchalei.xadmin.modules.sys.service.SysUserService
import org.babyfish.jimmer.client.meta.Api
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/user")
class SysCurrentUserController(private val sysUserService: SysUserService) {

    @GetMapping("/info")
    fun currentUserInfo(): R<SysUser> {
        val userInfo = this.sysUserService.currentUserInfo()
        return R(data = userInfo)
    }
}
