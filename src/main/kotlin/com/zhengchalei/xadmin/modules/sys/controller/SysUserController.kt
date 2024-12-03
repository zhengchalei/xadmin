/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
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
