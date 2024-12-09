/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
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
