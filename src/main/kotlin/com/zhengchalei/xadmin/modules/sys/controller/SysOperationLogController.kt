/*
Copyright 2024 [郑查磊]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.zhengchalei.xadmin.modules.sys.controller

import com.zhengchalei.xadmin.commons.QueryPage
import com.zhengchalei.xadmin.commons.R
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysOperationLogDetailView
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysOperationLogListSpecification
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysOperationLogPageView
import com.zhengchalei.xadmin.modules.sys.service.SysOperationLogService
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
    fun findSysOperationLogList(
        @NotNull specification: SysOperationLogListSpecification
    ): R<List<SysOperationLogPageView>> {
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
