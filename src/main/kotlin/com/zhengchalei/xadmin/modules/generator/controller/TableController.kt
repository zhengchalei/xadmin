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
package com.zhengchalei.xadmin.modules.generator.controller

import com.zhengchalei.xadmin.commons.QueryPage
import com.zhengchalei.xadmin.commons.R
import com.zhengchalei.xadmin.modules.generator.domain.dto.*
import com.zhengchalei.xadmin.modules.generator.service.TableService
import com.zhengchalei.xadmin.modules.sys.domain.Log
import com.zhengchalei.xadmin.modules.sys.domain.OperationType
import jakarta.validation.constraints.NotNull
import org.babyfish.jimmer.client.meta.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("generator")
@Validated
@RestController
@RequestMapping("/api/generator/table")
class TableController(val tableService: TableService) {
    @PreAuthorize("hasAuthority('sys:table:read') or hasAnyRole('admin')")
    @GetMapping("/id/{id}")
    fun findTableById(@PathVariable id: Long): R<TableDetailView> {
        val data = tableService.findTableById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:table:read') or hasAnyRole('admin')")
    @GetMapping("/list")
    fun findTableList(@NotNull specification: TableListSpecification): R<List<TablePageView>> {
        val data = tableService.findTableList(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:table:read') or hasAnyRole('admin')")
    @GetMapping("/page")
    fun findTablePage(
        @NotNull specification: TableListSpecification,
        @NotNull pageable: QueryPage,
    ): R<MutableList<TablePageView>> {
        val data = tableService.findTablePage(specification, pageable.page())
        return R.success(data)
    }

    @Log(value = "创建代码生成表", type = OperationType.CREATE)
    @PreAuthorize("hasAuthority('sys:table:create') or hasAnyRole('admin')")
    @PostMapping("/create")
    fun createTable(@NotNull @RequestBody tableCreateInput: TableCreateInput): R<TableDetailView> {
        val data = tableService.createTable(tableCreateInput)
        return R(data = data)
    }

    @Log(value = "修改代码生成表", type = OperationType.UPDATE)
    @PreAuthorize("hasAuthority('sys:table:edit') or hasAnyRole('admin')")
    @PostMapping("/update")
    fun updateTableById(@NotNull @RequestBody tableUpdateInput: TableUpdateInput): R<TableDetailView> {
        val data = tableService.updateTableById(tableUpdateInput)
        return R(data = data)
    }

    @Log(value = "删除代码生成表", type = OperationType.DELETE)
    @PreAuthorize("hasAuthority('sys:table:delete') or hasAnyRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteTableById(@NotNull @PathVariable id: Long): R<Boolean> {
        tableService.deleteTableById(id)
        return R()
    }

    // 生成代码
    @Log(value = "生成代码", type = OperationType.OTHER)
    @PostMapping("/generate/{id}")
    fun generate(@NotNull @PathVariable id: Long): R<Boolean> {
        tableService.generate(id)
        return R()
    }
}
