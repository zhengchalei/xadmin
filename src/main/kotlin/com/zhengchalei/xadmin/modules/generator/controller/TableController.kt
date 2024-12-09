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
