package com.zhengchalei.cloud.platform.modules.sys.controller

import com.zhengchalei.cloud.platform.commons.QueryPage
import com.zhengchalei.cloud.platform.commons.R
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.*
import com.zhengchalei.cloud.platform.modules.sys.service.SysDepartmentService
import jakarta.validation.constraints.NotNull
import org.babyfish.jimmer.client.meta.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/sys/department")
class SysDepartmentController(
    val sysDepartmentService: SysDepartmentService,
) {
    @PreAuthorize("hasAuthority('sys:department:id') or hasRole('admin')")
    @GetMapping("/id/{id}")
    fun findSysDepartmentById(
        @PathVariable id: Long,
    ): R<SysDepartmentDetailView> {
        val data = sysDepartmentService.findSysDepartmentById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:department:list') or hasRole('admin')")
    @GetMapping("/list")
    fun findSysDepartmentList(
        @NotNull specification: SysDepartmentPageSpecification,
    ): R<List<SysDepartmentPageView>> {
        val data = sysDepartmentService.findSysDepartmentList(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:department:tree-root') or hasRole('admin')")
    @GetMapping("/tree-root")
    fun findSysDepartmentTreeRoot(specification: SysDepartmentPageSpecification): R<List<SysDepartmentTreeRootView>> {
        val data = sysDepartmentService.findSysDepartmentTreeRoot(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:department:page') or hasRole('admin')")
    @GetMapping("/page")
    fun findSysDepartmentPage(
        @NotNull specification: SysDepartmentPageSpecification,
        @NotNull pageable: QueryPage,
    ): R<MutableList<SysDepartmentPageView>> {
        val data = sysDepartmentService.findSysDepartmentPage(specification, pageable.page())
        return R.success(data)
    }

    @PreAuthorize("hasAuthority('sys:department:tree') or hasRole('admin')")
    @GetMapping("/tree")
    fun findSysDepartmentTree(specification: SysDepartmentPageSpecification): R<List<SysDepartmentTreeView>> {
        val data = sysDepartmentService.findSysDepartmentTree(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:department:tree-select') or hasRole('admin')")
    @GetMapping("/tree-select")
    fun findSysDepartmentTreeSelect(specification: SysDepartmentPageSpecification): R<List<SysDepartmentTreeSelectView>> {
        val data = sysDepartmentService.findSysDepartmentTreeSelect(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:department:create') or hasRole('admin')")
    @PostMapping("/create")
    fun createSysDepartment(
        @NotNull @RequestBody sysDepartmentCreateInput: SysDepartmentCreateInput,
    ): R<SysDepartmentDetailView> {
        val data = sysDepartmentService.createSysDepartment(sysDepartmentCreateInput)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:department:update') or hasRole('admin')")
    @PostMapping("/update")
    fun updateSysDepartmentById(
        @NotNull @RequestBody sysDepartmentUpdateInput: SysDepartmentUpdateInput,
    ): R<SysDepartmentDetailView> {
        val data = sysDepartmentService.updateSysDepartmentById(sysDepartmentUpdateInput)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:department:delete') or hasRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteSysDepartmentById(
        @NotNull @PathVariable id: Long,
    ): R<Boolean> {
        sysDepartmentService.deleteSysDepartmentById(id)
        return R()
    }
}
