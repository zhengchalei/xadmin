package com.zhengchalei.cloud.platform.modules.sys.controller

import com.zhengchalei.cloud.platform.commons.QueryPage
import com.zhengchalei.cloud.platform.commons.R
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.*
import com.zhengchalei.cloud.platform.modules.sys.service.SysPostsService
import jakarta.validation.constraints.NotNull
import org.babyfish.jimmer.client.meta.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/sys/posts")
class SysPostsController(
    val sysPostsService: SysPostsService
) {

    @PreAuthorize("hasAuthority('sys:post:id') or hasRole('admin')")
    @GetMapping("/id/{id}")
    fun findSysPostsById(@PathVariable id: Long): R<SysPostsDetailView> {
        val data = sysPostsService.findSysPostsById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:post:list') or hasRole('admin')")
    @GetMapping("/list")
    fun findSysPostsList(@NotNull specification: SysPostsPageSpecification): R<List<SysPostsPageView>> {
        val data = sysPostsService.findSysPostsList(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:post:page') or hasRole('admin')")
    @GetMapping("/page")
    fun findSysPostsPage(@NotNull specification: SysPostsPageSpecification, @NotNull pageable: QueryPage): R<MutableList<SysPostsPageView>> {
        val data = sysPostsService.findSysPostsPage(specification, pageable.page())
        return R.success(data)
    }

    @PreAuthorize("hasAuthority('sys:post:create') or hasRole('admin')")
    @PostMapping("/create")
    fun createSysPosts(@NotNull @RequestBody sysPostsCreateInput: SysPostsCreateInput): R<SysPostsDetailView> {
        val data = sysPostsService.createSysPosts(sysPostsCreateInput)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:post:update') or hasRole('admin')")
    @PostMapping("/update")
    fun updateSysPostsById(@NotNull @RequestBody sysPostsUpdateInput: SysPostsUpdateInput): R<SysPostsDetailView> {
        val data = sysPostsService.updateSysPostsById(sysPostsUpdateInput)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:post:delete') or hasRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteSysPostsById(@NotNull @PathVariable id: Long): R<Boolean> {
        sysPostsService.deleteSysPostsById(id)
        return R()
    }
}