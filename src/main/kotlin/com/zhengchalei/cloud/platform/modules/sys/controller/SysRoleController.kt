/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.controller

import com.zhengchalei.cloud.platform.commons.QueryPage
import com.zhengchalei.cloud.platform.commons.R
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.*
import com.zhengchalei.cloud.platform.modules.sys.service.SysRoleService
import jakarta.validation.constraints.NotNull
import org.babyfish.jimmer.client.meta.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/sys/role")
class SysRoleController(val sysRoleService: SysRoleService) {
    @PreAuthorize("hasAuthority('sys:role:id') or hasAnyRole('admin')")
    @GetMapping("/id/{id}")
    fun findSysRoleById(@PathVariable id: Long): R<SysRoleDetailView> {
        val data = sysRoleService.findSysRoleById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:role:list') or hasAnyRole('admin')")
    @GetMapping("/list")
    fun findSysRoleList(@NotNull specification: SysRoleListSpecification): R<List<SysRolePageView>> {
        val data = sysRoleService.findSysRoleList(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:role:page') or hasAnyRole('admin')")
    @GetMapping("/page")
    fun findSysRolePage(
        @NotNull specification: SysRoleListSpecification,
        @NotNull pageable: QueryPage,
    ): R<MutableList<SysRolePageView>> {
        val data = sysRoleService.findSysRolePage(specification, pageable.page())
        return R.success(data)
    }

    @PreAuthorize("hasAuthority('sys:role:create') or hasAnyRole('admin')")
    @PostMapping("/create")
    fun createSysRole(@NotNull @RequestBody sysRoleCreateInput: SysRoleCreateInput): R<SysRoleDetailView> {
        val data = sysRoleService.createSysRole(sysRoleCreateInput)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:role:update') or hasAnyRole('admin')")
    @PostMapping("/update")
    fun updateSysRoleById(@NotNull @RequestBody sysRoleUpdateInput: SysRoleUpdateInput): R<SysRoleDetailView> {
        val data = sysRoleService.updateSysRoleById(sysRoleUpdateInput)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:role:delete') or hasAnyRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteSysRoleById(@NotNull @PathVariable id: Long): R<Boolean> {
        sysRoleService.deleteSysRoleById(id)
        return R()
    }
}
