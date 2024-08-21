package com.zhengchalei.cloud.platform.modules.sys.controller

import com.zhengchalei.cloud.platform.commons.QueryPage
import com.zhengchalei.cloud.platform.commons.R
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.*
import com.zhengchalei.cloud.platform.modules.sys.service.SysUserService
import jakarta.validation.constraints.NotNull
import org.babyfish.jimmer.client.meta.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/sys/user")
class SysUserController(
    val sysUserService: SysUserService
) {

    @PreAuthorize("hasAuthority('sys:user:id') or hasRole('admin')")
    @GetMapping("/id/{id}")
    fun findSysUserById(@PathVariable id: Long): R<SysUserDetailView> {
        val data = sysUserService.findSysUserById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:user:list') or hasRole('admin')")
    @GetMapping("/list")
    fun findSysUserList(@NotNull specification: SysUserPageSpecification): R<List<SysUserPageView>> {
        val data = sysUserService.findSysUserList(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:user:page') or hasRole('admin')")
    @GetMapping("/page")
    fun findSysUserPage(@NotNull specification: SysUserPageSpecification, @NotNull pageable: QueryPage): R<MutableList<SysUserPageView>> {
        val data = sysUserService.findSysUserPage(specification, pageable.page())
        return R.success(data)
    }

    @PreAuthorize("hasAuthority('sys:user:create') or hasRole('admin')")
    @PostMapping("/create")
    fun createSysUser(@NotNull @RequestBody sysUserCreateInput: SysUserCreateInput): R<SysUserDetailView> {
        val data = sysUserService.createSysUser(sysUserCreateInput)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:user:update') or hasRole('admin')")
    @PostMapping("/update")
    fun updateSysUserPageById(@NotNull @RequestBody sysUserUpdateInput: SysUserUpdateInput): R<SysUserDetailView> {
        val data = sysUserService.updateSysUserPageById(sysUserUpdateInput)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:user:delete') or hasRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteSysUserPageById(@NotNull @PathVariable id: Long): R<Boolean> {
        sysUserService.deleteSysUserPageById(id)
        return R()
    }
}