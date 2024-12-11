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
import com.zhengchalei.xadmin.modules.generator.service.TableColumnService
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
@RequestMapping("/api/generator/table-column")
class TableColumnController(val tableColumnService: TableColumnService) {
    @PreAuthorize("hasAuthority('sys:table-column:read') or hasAnyRole('admin')")
    @GetMapping("/id/{id}")
    fun findTableColumnById(@PathVariable id: Long): R<TableColumnDetailView> {
        val data = tableColumnService.findTableColumnById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:table-column:read') or hasAnyRole('admin')")
    @GetMapping("/list")
    fun findTableColumnList(@NotNull specification: TableColumnListSpecification): R<List<TableColumnPageView>> {
        val data = tableColumnService.findTableColumnList(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:table-column:read') or hasAnyRole('admin')")
    @GetMapping("/page")
    fun findTableColumnPage(
        @NotNull specification: TableColumnListSpecification,
        @NotNull pageable: QueryPage,
    ): R<MutableList<TableColumnPageView>> {
        val data = tableColumnService.findTableColumnPage(specification, pageable.page())
        return R.success(data)
    }

    @Log(value = "创建代码生成表列", type = OperationType.CREATE)
    @PreAuthorize("hasAuthority('sys:table-column:create') or hasAnyRole('admin')")
    @PostMapping("/create")
    fun createTableColumn(
        @NotNull @RequestBody tableColumnCreateInput: TableColumnCreateInput
    ): R<TableColumnDetailView> {
        val data = tableColumnService.createTableColumn(tableColumnCreateInput)
        return R(data = data)
    }

    @Log(value = "修改代码生成表列", type = OperationType.UPDATE)
    @PreAuthorize("hasAuthority('sys:table-column:edit') or hasAnyRole('admin')")
    @PostMapping("/update")
    fun updateTableColumnById(
        @NotNull @RequestBody tableColumnUpdateInput: TableColumnUpdateInput
    ): R<TableColumnDetailView> {
        val data = tableColumnService.updateTableColumnById(tableColumnUpdateInput)
        return R(data = data)
    }

    @Log(value = "删除代码生成表列", type = OperationType.DELETE)
    @PreAuthorize("hasAuthority('sys:table-column:delete') or hasAnyRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteTableColumnById(@NotNull @PathVariable id: Long): R<Boolean> {
        tableColumnService.deleteTableColumnById(id)
        return R()
    }
}
