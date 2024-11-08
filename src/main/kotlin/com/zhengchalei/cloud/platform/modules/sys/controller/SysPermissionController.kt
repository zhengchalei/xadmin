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
import com.zhengchalei.cloud.platform.modules.sys.service.SysPermissionService
import jakarta.validation.constraints.NotNull
import org.babyfish.jimmer.client.meta.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/sys/permission")
class SysPermissionController(val sysPermissionService: SysPermissionService) {
    @PreAuthorize("hasAuthority('sys:permission:read') or hasAnyRole('admin')")
    @GetMapping("/id/{id}")
    fun findSysPermissionById(@PathVariable id: Long): R<SysPermissionDetailView> {
        val data = sysPermissionService.findSysPermissionById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:permission:read') or hasAnyRole('admin')")
    @GetMapping("/list")
    fun findSysPermissionList(@NotNull specification: SysPermissionListSpecification): R<List<SysPermissionPageView>> {
        val data = sysPermissionService.findSysPermissionList(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:permission:tree-root') or hasAnyRole('admin')")
    @GetMapping("/tree-root")
    fun findSysPermissionTreeRoot(specification: SysPermissionListSpecification): R<List<SysPermissionTreeRootView>> {
        val data = sysPermissionService.findSysPermissionTreeRoot(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:permission:tree') or hasAnyRole('admin')")
    @GetMapping("/tree")
    fun findSysPermissionTree(specification: SysPermissionListSpecification): R<List<SysPermissionTreeView>> {
        val data = sysPermissionService.findSysPermissionTree(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:permission:tree-select') or hasAnyRole('admin')")
    @GetMapping("/tree-select")
    fun findSysPermissionTreeSelect(
        specification: SysPermissionListSpecification
    ): R<List<SysPermissionTreeSelectView>> {
        val data = sysPermissionService.findSysPermissionTreeSelect(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:permission:read') or hasAnyRole('admin')")
    @GetMapping("/page")
    fun findSysPermissionPage(
        @NotNull specification: SysPermissionListSpecification,
        @NotNull pageable: QueryPage,
    ): R<MutableList<SysPermissionPageView>> {
        val data = sysPermissionService.findSysPermissionPage(specification, pageable.page())
        return R.success(data)
    }

    @PreAuthorize("hasAuthority('sys:permission:create') or hasAnyRole('admin')")
    @PostMapping("/create")
    fun createSysPermission(
        @NotNull @RequestBody sysPermissionCreateInput: SysPermissionCreateInput
    ): R<SysPermissionDetailView> {
        val data = sysPermissionService.createSysPermission(sysPermissionCreateInput)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:permission:update') or hasAnyRole('admin')")
    @PostMapping("/update")
    fun updateSysPermissionById(
        @NotNull @RequestBody sysPermissionUpdateInput: SysPermissionUpdateInput
    ): R<SysPermissionDetailView> {
        val data = sysPermissionService.updateSysPermissionById(sysPermissionUpdateInput)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:permission:delete') or hasAnyRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteSysPermissionById(@NotNull @PathVariable id: Long): R<Boolean> {
        sysPermissionService.deleteSysPermissionById(id)
        return R()
    }
}
