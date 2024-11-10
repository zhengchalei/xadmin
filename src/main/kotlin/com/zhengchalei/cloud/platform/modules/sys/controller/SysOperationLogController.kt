/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.controller

import com.zhengchalei.cloud.platform.commons.QueryPage
import com.zhengchalei.cloud.platform.commons.R
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysOperationLogDetailView
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysOperationLogListSpecification
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysOperationLogPageView
import com.zhengchalei.cloud.platform.modules.sys.service.SysOperationLogService
import jakarta.validation.constraints.NotNull
import org.babyfish.jimmer.client.meta.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/sys/operation-log")
class SysOperationLogController(val sysOperationLogService: SysOperationLogService) {
    @PreAuthorize("hasAuthority('sys:operation-log:read') or hasAnyRole('admin')")
    @GetMapping("/id/{id}")
    fun findSysOperationLogById(@PathVariable id: Long): R<SysOperationLogDetailView> {
        val data = sysOperationLogService.findSysOperationLogById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:operation-log:read') or hasAnyRole('admin')")
    @GetMapping("/list")
    fun findSysOperationLogList(@NotNull specification: SysOperationLogListSpecification): R<List<SysOperationLogPageView>> {
        val data = sysOperationLogService.findSysOperationLogList(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:operation-log:read') or hasAnyRole('admin')")
    @GetMapping("/page")
    fun findSysOperationLogPage(
        @NotNull specification: SysOperationLogListSpecification,
        @NotNull pageable: QueryPage,
    ): R<MutableList<SysOperationLogPageView>> {
        val data = sysOperationLogService.findSysOperationLogPage(specification, pageable.page())
        return R.success(data)
    }

    @PreAuthorize("hasAuthority('sys:operation-log:delete') or hasAnyRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteSysOperationLogById(@NotNull @PathVariable id: Long): R<Boolean> {
        sysOperationLogService.deleteSysOperationLogById(id)
        return R()
    }
}
