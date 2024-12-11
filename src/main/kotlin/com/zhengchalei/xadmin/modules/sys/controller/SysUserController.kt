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

import com.zhengchalei.xadmin.commons.QueryPage
import com.zhengchalei.xadmin.commons.R
import com.zhengchalei.xadmin.modules.sys.domain.Log
import com.zhengchalei.xadmin.modules.sys.domain.OperationType
import com.zhengchalei.xadmin.modules.sys.domain.dto.*
import com.zhengchalei.xadmin.modules.sys.service.SysUserService
import jakarta.validation.constraints.NotNull
import org.babyfish.jimmer.client.meta.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/sys/user")
class SysUserController(val sysUserService: SysUserService) {
    @PreAuthorize("hasAuthority('sys:user:read') or hasAnyRole('admin')")
    @GetMapping("/id/{id}")
    fun findSysUserById(@PathVariable id: Long): R<SysUserDetailView> {
        val data = sysUserService.findSysUserById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:user:read') or hasAnyRole('admin')")
    @GetMapping("/list")
    fun findSysUserList(@NotNull specification: SysUserListSpecification): R<List<SysUserPageView>> {
        val data = sysUserService.findSysUserList(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:user:read') or hasAnyRole('admin')")
    @GetMapping("/page")
    fun findSysUserPage(
        @NotNull specification: SysUserListSpecification,
        @NotNull pageable: QueryPage,
    ): R<MutableList<SysUserPageView>> {
        val data = sysUserService.findSysUserPage(specification, pageable.page())
        return R.success(data)
    }

    @Log(value = "创建系统用户", type = OperationType.CREATE)
    @PreAuthorize("hasAuthority('sys:user:create') or hasAnyRole('admin')")
    @PostMapping("/create")
    fun createSysUser(@NotNull @RequestBody sysUserCreateInput: SysUserCreateInput): R<SysUserDetailView> {
        val data = sysUserService.createSysUser(sysUserCreateInput)
        return R(data = data)
    }

    @Log(value = "修改系统用户", type = OperationType.UPDATE)
    @PreAuthorize("hasAuthority('sys:user:edit') or hasAnyRole('admin')")
    @PostMapping("/update")
    fun updateSysUserById(@NotNull @RequestBody sysUserUpdateInput: SysUserUpdateInput): R<SysUserDetailView> {
        val data = sysUserService.updateSysUserById(sysUserUpdateInput)
        return R(data = data)
    }

    @Log(value = "删除系统用户", type = OperationType.DELETE)
    @PreAuthorize("hasAuthority('sys:user:delete') or hasAnyRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteSysUserById(@NotNull @PathVariable id: Long): R<Boolean> {
        sysUserService.deleteSysUserById(id)
        return R()
    }
}
