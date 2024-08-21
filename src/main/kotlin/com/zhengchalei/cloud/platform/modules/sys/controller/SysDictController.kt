package com.zhengchalei.cloud.platform.modules.sys.controller

import com.zhengchalei.cloud.platform.commons.QueryPage
import com.zhengchalei.cloud.platform.commons.R
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.*
import com.zhengchalei.cloud.platform.modules.sys.service.SysDictService
import jakarta.validation.constraints.NotNull
import org.babyfish.jimmer.client.meta.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/sys/dict")
class SysDictController(
    val sysDictService: SysDictService
) {

    @PreAuthorize("hasAuthority('sys:dict:id') or hasRole('admin')")
    @GetMapping("/id/{id}")
    fun findSysDictById(@PathVariable id: Long): R<SysDictDetailView> {
        val data = sysDictService.findSysDictById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:dict:list') or hasRole('admin')")
    @GetMapping("/list")
    fun findSysDictList(@NotNull specification: SysDictPageSpecification): R<List<SysDictPageView>> {
        val data = sysDictService.findSysDictList(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:dict:page') or hasRole('admin')")
    @GetMapping("/page")
    fun findSysDictPage(@NotNull specification: SysDictPageSpecification, @NotNull pageable: QueryPage): R<MutableList<SysDictPageView>> {
        val data = sysDictService.findSysDictPage(specification, pageable.page())
        return R.success(data)
    }

    @PreAuthorize("hasAuthority('sys:dict:create') or hasRole('admin')")
    @PostMapping("/create")
    fun createSysDict(@NotNull @RequestBody sysDictCreateInput: SysDictCreateInput): R<SysDictDetailView> {
        val data = sysDictService.createSysDict(sysDictCreateInput)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:dict:update') or hasRole('admin')")
    @PostMapping("/update")
    fun updateSysDictById(@NotNull @RequestBody sysDictUpdateInput: SysDictUpdateInput): R<SysDictDetailView> {
        val data = sysDictService.updateSysDictById(sysDictUpdateInput)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:dict:delete') or hasRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteSysDictById(@NotNull @PathVariable id: Long): R<Boolean> {
        sysDictService.deleteSysDictById(id)
        return R()
    }
}