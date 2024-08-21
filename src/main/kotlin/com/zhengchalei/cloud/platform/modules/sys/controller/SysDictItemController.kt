package com.zhengchalei.cloud.platform.modules.sys.controller

import com.zhengchalei.cloud.platform.commons.QueryPage
import com.zhengchalei.cloud.platform.commons.R
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.*
import com.zhengchalei.cloud.platform.modules.sys.service.SysDictItemService
import jakarta.validation.constraints.NotNull
import org.babyfish.jimmer.client.meta.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/sys/dict-item")
class SysDictItemController(
    val sysDictItemService: SysDictItemService
) {

    @PreAuthorize("hasAuthority('sys:dict-item:id') or hasRole('admin')")
    @GetMapping("/id/{id}")
    fun findSysDictItemById(@PathVariable id: Long): R<SysDictItemDetailView> {
        val data = sysDictItemService.findSysDictItemById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:dict-item:list') or hasRole('admin')")
    @GetMapping("/list")
    fun findSysDictItemList(@NotNull specification: SysDictItemPageSpecification): R<List<SysDictItemPageView>> {
        val data = sysDictItemService.findSysDictItemList(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:dict-item:page') or hasRole('admin')")
    @GetMapping("/page")
    fun findSysDictItemPage(
        @NotNull specification: SysDictItemPageSpecification,
        @NotNull pageable: QueryPage
    ): R<MutableList<SysDictItemPageView>> {
        val data = sysDictItemService.findSysDictItemPage(specification, pageable.page())
        return R.success(data)
    }

    @PreAuthorize("hasAuthority('sys:dict-item:create') or hasRole('admin')")
    @PostMapping("/create")
    fun createSysDictItem(@NotNull @RequestBody sysDictItemCreateInput: SysDictItemCreateInput): R<SysDictItemDetailView> {
        val data = sysDictItemService.createSysDictItem(sysDictItemCreateInput)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:dict-item:update') or hasRole('admin')")
    @PostMapping("/update")
    fun updateSysDictItemById(@NotNull @RequestBody sysDictItemUpdateInput: SysDictItemUpdateInput): R<SysDictItemDetailView> {
        val data = sysDictItemService.updateSysDictItemById(sysDictItemUpdateInput)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:dict-item:delete') or hasRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteSysDictItemById(@NotNull @PathVariable id: Long): R<Boolean> {
        sysDictItemService.deleteSysDictItemById(id)
        return R()
    }
}