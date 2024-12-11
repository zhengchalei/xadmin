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
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysLoginLogDetailView
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysLoginLogListSpecification
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysLoginLogPageView
import com.zhengchalei.xadmin.modules.sys.service.SysLoginLogService
import jakarta.validation.constraints.NotNull
import org.babyfish.jimmer.client.meta.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/sys/login-log")
class SysLoginLogController(val sysLoginLogService: SysLoginLogService) {
    @PreAuthorize("hasAuthority('sys:login-log:read') or hasAnyRole('admin')")
    @GetMapping("/id/{id}")
    fun findSysLoginLogById(@PathVariable id: Long): R<SysLoginLogDetailView> {
        val data = sysLoginLogService.findSysLoginLogById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:login-log:read') or hasAnyRole('admin')")
    @GetMapping("/list")
    fun findSysLoginLogList(@NotNull specification: SysLoginLogListSpecification): R<List<SysLoginLogPageView>> {
        val data = sysLoginLogService.findSysLoginLogList(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:login-log:read') or hasAnyRole('admin')")
    @GetMapping("/page")
    fun findSysLoginLogPage(
        @NotNull specification: SysLoginLogListSpecification,
        @NotNull pageable: QueryPage,
    ): R<MutableList<SysLoginLogPageView>> {
        val data = sysLoginLogService.findSysLoginLogPage(specification, pageable.page())
        return R.success(data)
    }

    @PreAuthorize("hasAuthority('sys:login-log:delete') or hasAnyRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteSysLoginLogById(@NotNull @PathVariable id: Long): R<Boolean> {
        sysLoginLogService.deleteSysLoginLogById(id)
        return R()
    }
}
