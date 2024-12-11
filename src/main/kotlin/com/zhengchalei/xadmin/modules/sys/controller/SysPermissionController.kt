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
import com.zhengchalei.xadmin.modules.sys.service.SysPermissionService
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

    @PreAuthorize("hasAuthority('sys:permission:read') or hasAnyRole('admin')")
    @GetMapping("/tree-root")
    fun findSysPermissionTreeRoot(specification: SysPermissionListSpecification): R<List<SysPermissionTreeRootView>> {
        val data = sysPermissionService.findSysPermissionTreeRoot(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:permission:read') or hasAnyRole('admin')")
    @GetMapping("/tree")
    fun findSysPermissionTree(specification: SysPermissionListSpecification): R<List<SysPermissionTreeView>> {
        val data = sysPermissionService.findSysPermissionTree(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:permission:read') or hasAnyRole('admin')")
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

    @Log(value = "创建系统权限", type = OperationType.CREATE)
    @PreAuthorize("hasAuthority('sys:permission:create') or hasAnyRole('admin')")
    @PostMapping("/create")
    fun createSysPermission(
        @NotNull @RequestBody sysPermissionCreateInput: SysPermissionCreateInput
    ): R<SysPermissionDetailView> {
        val data = sysPermissionService.createSysPermission(sysPermissionCreateInput)
        return R(data = data)
    }

    @Log(value = "修改系统权限", type = OperationType.UPDATE)
    @PreAuthorize("hasAuthority('sys:permission:edit') or hasAnyRole('admin')")
    @PostMapping("/update")
    fun updateSysPermissionById(
        @NotNull @RequestBody sysPermissionUpdateInput: SysPermissionUpdateInput
    ): R<SysPermissionDetailView> {
        val data = sysPermissionService.updateSysPermissionById(sysPermissionUpdateInput)
        return R(data = data)
    }

    @Log(value = "删除系统权限", type = OperationType.DELETE)
    @PreAuthorize("hasAuthority('sys:permission:delete') or hasAnyRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteSysPermissionById(@NotNull @PathVariable id: Long): R<Boolean> {
        sysPermissionService.deleteSysPermissionById(id)
        return R()
    }
}
