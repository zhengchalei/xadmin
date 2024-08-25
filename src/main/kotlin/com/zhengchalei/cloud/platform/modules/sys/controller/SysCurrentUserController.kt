package com.zhengchalei.cloud.platform.modules.sys.controller

import com.zhengchalei.cloud.platform.commons.R
import com.zhengchalei.cloud.platform.modules.sys.domain.SysUser
import com.zhengchalei.cloud.platform.modules.sys.service.SysUserService
import org.babyfish.jimmer.client.meta.Api
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/user")
class SysCurrentUserController(
    val sysUserService: SysUserService,
) {
    @GetMapping("/info")
    fun currentUserInfo(): R<SysUser> {
        val userInfo = this.sysUserService.currentUserInfo()
        return R(data = userInfo)
    }
}
