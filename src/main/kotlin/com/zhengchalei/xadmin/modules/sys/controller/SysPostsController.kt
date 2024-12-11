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
import com.zhengchalei.xadmin.modules.sys.service.SysPostsService
import jakarta.validation.constraints.NotNull
import org.babyfish.jimmer.client.meta.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/sys/posts")
class SysPostsController(val sysPostsService: SysPostsService) {
    @PreAuthorize("hasAuthority('sys:posts:read') or hasAnyRole('admin')")
    @GetMapping("/id/{id}")
    fun findSysPostsById(@PathVariable id: Long): R<SysPostsDetailView> {
        val data = sysPostsService.findSysPostsById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:posts:read') or hasAnyRole('admin')")
    @GetMapping("/list")
    fun findSysPostsList(@NotNull specification: SysPostsListSpecification): R<List<SysPostsPageView>> {
        val data = sysPostsService.findSysPostsList(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:posts:read') or hasAnyRole('admin')")
    @GetMapping("/page")
    fun findSysPostsPage(
        @NotNull specification: SysPostsListSpecification,
        @NotNull pageable: QueryPage,
    ): R<MutableList<SysPostsPageView>> {
        val data = sysPostsService.findSysPostsPage(specification, pageable.page())
        return R.success(data)
    }

    @Log(value = "创建岗位", type = OperationType.CREATE)
    @PreAuthorize("hasAuthority('sys:posts:create') or hasAnyRole('admin')")
    @PostMapping("/create")
    fun createSysPosts(@NotNull @RequestBody sysPostsCreateInput: SysPostsCreateInput): R<SysPostsDetailView> {
        val data = sysPostsService.createSysPosts(sysPostsCreateInput)
        return R(data = data)
    }

    @Log(value = "修改岗位", type = OperationType.UPDATE)
    @PreAuthorize("hasAuthority('sys:posts:edit') or hasAnyRole('admin')")
    @PostMapping("/update")
    fun updateSysPostsById(@NotNull @RequestBody sysPostsUpdateInput: SysPostsUpdateInput): R<SysPostsDetailView> {
        val data = sysPostsService.updateSysPostsById(sysPostsUpdateInput)
        return R(data = data)
    }

    @Log(value = "删除岗位", type = OperationType.DELETE)
    @PreAuthorize("hasAuthority('sys:posts:delete') or hasAnyRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteSysPostsById(@NotNull @PathVariable id: Long): R<Boolean> {
        sysPostsService.deleteSysPostsById(id)
        return R()
    }
}
