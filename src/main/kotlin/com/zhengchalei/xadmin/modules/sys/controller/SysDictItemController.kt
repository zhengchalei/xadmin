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
import com.zhengchalei.xadmin.modules.sys.service.SysDictItemService
import jakarta.validation.constraints.NotNull
import org.babyfish.jimmer.client.meta.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/sys/dict-item")
class SysDictItemController(val sysDictItemService: SysDictItemService) {
    @PreAuthorize("hasAuthority('sys:dict-item:read') or hasAnyRole('admin')")
    @GetMapping("/id/{id}")
    fun findSysDictItemById(@PathVariable id: Long): R<SysDictItemDetailView> {
        val data = sysDictItemService.findSysDictItemById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:dict-item:read') or hasAnyRole('admin')")
    @GetMapping("/list")
    fun findSysDictItemList(@NotNull specification: SysDictItemListSpecification): R<List<SysDictItemPageView>> {
        val data = sysDictItemService.findSysDictItemList(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:dict-item:read') or hasAnyRole('admin')")
    @GetMapping("/page")
    fun findSysDictItemPage(
        @NotNull specification: SysDictItemListSpecification,
        @NotNull pageable: QueryPage,
    ): R<MutableList<SysDictItemPageView>> {
        val data = sysDictItemService.findSysDictItemPage(specification, pageable.page())
        return R.success(data)
    }

    @Log(value = "创建系统字典", type = OperationType.CREATE)
    @PreAuthorize("hasAuthority('sys:dict-item:create') or hasAnyRole('admin')")
    @PostMapping("/create")
    fun createSysDictItem(
        @NotNull @RequestBody sysDictItemCreateInput: SysDictItemCreateInput
    ): R<SysDictItemDetailView> {
        val data = sysDictItemService.createSysDictItem(sysDictItemCreateInput)
        return R(data = data)
    }

    @Log(value = "修改系统字典", type = OperationType.UPDATE)
    @PreAuthorize("hasAuthority('sys:dict-item:edit') or hasAnyRole('admin')")
    @PostMapping("/update")
    fun updateSysDictItemById(
        @NotNull @RequestBody sysDictItemUpdateInput: SysDictItemUpdateInput
    ): R<SysDictItemDetailView> {
        val data = sysDictItemService.updateSysDictItemById(sysDictItemUpdateInput)
        return R(data = data)
    }

    @Log(value = "删除系统字典", type = OperationType.DELETE)
    @PreAuthorize("hasAuthority('sys:dict-item:delete') or hasAnyRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteSysDictItemById(@NotNull @PathVariable id: Long): R<Boolean> {
        sysDictItemService.deleteSysDictItemById(id)
        return R()
    }
}
