package com.zhengchalei.cloud.platform.modules.sys.controller

import com.zhengchalei.cloud.platform.commons.QueryPage
import com.zhengchalei.cloud.platform.commons.R
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysLoginLogDetailView
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysLoginLogPageSpecification
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysLoginLogPageView
import com.zhengchalei.cloud.platform.modules.sys.service.SysLoginLogService
import jakarta.validation.constraints.NotNull
import org.babyfish.jimmer.client.meta.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/sys/login-log")
class SysLoginLogController(
    val sysLoginLogService: SysLoginLogService,
) {
    @PreAuthorize("hasAuthority('sys:loginLog:id') or hasLoginLog('admin')")
    @GetMapping("/id/{id}")
    fun findSysLoginLogById(
        @PathVariable id: Long,
    ): R<SysLoginLogDetailView> {
        val data = sysLoginLogService.findSysLoginLogById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:loginLog:list') or hasLoginLog('admin')")
    @GetMapping("/list")
    fun findSysLoginLogList(
        @NotNull specification: SysLoginLogPageSpecification,
    ): R<List<SysLoginLogPageView>> {
        val data = sysLoginLogService.findSysLoginLogList(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:loginLog:page') or hasLoginLog('admin')")
    @GetMapping("/page")
    fun findSysLoginLogPage(
        @NotNull specification: SysLoginLogPageSpecification,
        @NotNull pageable: QueryPage,
    ): R<MutableList<SysLoginLogPageView>> {
        val data = sysLoginLogService.findSysLoginLogPage(specification, pageable.page())
        return R.success(data)
    }

    @PreAuthorize("hasAuthority('sys:loginLog:delete') or hasLoginLog('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteSysLoginLogById(
        @NotNull @PathVariable id: Long,
    ): R<Boolean> {
        sysLoginLogService.deleteSysLoginLogById(id)
        return R()
    }
}
