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
import com.zhengchalei.xadmin.modules.sys.service.SysDictService
import jakarta.validation.constraints.NotNull
import org.babyfish.jimmer.client.meta.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/sys/dict")
class SysDictController(val sysDictService: SysDictService) {
    @PreAuthorize("hasAuthority('sys:dict:read') or hasAnyRole('admin')")
    @GetMapping("/id/{id}")
    fun findSysDictById(@PathVariable id: Long): R<SysDictDetailView> {
        val data = sysDictService.findSysDictById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:dict:read') or hasAnyRole('admin')")
    @GetMapping("/list")
    fun findSysDictList(@NotNull specification: SysDictListSpecification): R<List<SysDictPageView>> {
        val data = sysDictService.findSysDictList(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:dict:read') or hasAnyRole('admin')")
    @GetMapping("/page")
    fun findSysDictPage(
        @NotNull specification: SysDictListSpecification,
        @NotNull pageable: QueryPage,
    ): R<MutableList<SysDictPageView>> {
        val data = sysDictService.findSysDictPage(specification, pageable.page())
        return R.success(data)
    }

    @Log(value = "创建系统字典", type = OperationType.CREATE)
    @PreAuthorize("hasAuthority('sys:dict:create') or hasAnyRole('admin')")
    @PostMapping("/create")
    fun createSysDict(@NotNull @RequestBody sysDictCreateInput: SysDictCreateInput): R<SysDictDetailView> {
        val data = sysDictService.createSysDict(sysDictCreateInput)
        return R(data = data)
    }

    @Log(value = "修改系统字典", type = OperationType.UPDATE)
    @PreAuthorize("hasAuthority('sys:dict:edit') or hasAnyRole('admin')")
    @PostMapping("/update")
    fun updateSysDictById(@NotNull @RequestBody sysDictUpdateInput: SysDictUpdateInput): R<SysDictDetailView> {
        val data = sysDictService.updateSysDictById(sysDictUpdateInput)
        return R(data = data)
    }

    @Log(value = "删除系统字典", type = OperationType.DELETE)
    @PreAuthorize("hasAuthority('sys:dict:delete') or hasAnyRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteSysDictById(@NotNull @PathVariable id: Long): R<Boolean> {
        sysDictService.deleteSysDictById(id)
        return R()
    }
}
