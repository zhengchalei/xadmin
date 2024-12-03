/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.controller

import com.zhengchalei.cloud.platform.commons.R
import com.zhengchalei.cloud.platform.config.security.SecurityUtils
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
class SysCurrentUserController(val sysUserService: SysUserService) {

    @GetMapping("/info")
    fun currentUserInfo(): R<SysUser> {
        val currentUserId = SecurityUtils.getCurrentUserId()
        val userInfo = this.sysUserService.currentUserInfo()
        return R(data = userInfo)
    }
}
