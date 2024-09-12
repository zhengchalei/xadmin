/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.controller

import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.commons.QueryPage
import com.zhengchalei.cloud.platform.commons.R
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.*
import com.zhengchalei.cloud.platform.modules.sys.service.SysTenantService
import org.babyfish.jimmer.client.meta.Api
import org.jetbrains.annotations.NotNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/sys/tenant")
class SysTenantController(val sysTenantService: SysTenantService) {

    @PreAuthorize(Const.ONLY_ROOT_AUTHORIZE_EL)
    @GetMapping("/id/{id}")
    fun findSysTenantById(@PathVariable id: Long): R<SysTenantDetailView> {
        val data = sysTenantService.findSysTenantById(id)
        return R(data = data)
    }

    @PreAuthorize(Const.ONLY_ROOT_AUTHORIZE_EL)
    @GetMapping("/list")
    fun findSysTenantList(@NotNull specification: SysTenantPageSpecification): R<List<SysTenantPageView>> {
        val data = sysTenantService.findSysTenantList(specification)
        return R(data = data)
    }

    @PreAuthorize(Const.ONLY_ROOT_AUTHORIZE_EL)
    @GetMapping("/page")
    fun findSysTenantPage(
        @NotNull specification: SysTenantPageSpecification,
        @NotNull pageable: QueryPage,
    ): R<MutableList<SysTenantPageView>> {
        val data = sysTenantService.findSysTenantPage(specification, pageable.page())
        return R.success(data)
    }

    @PreAuthorize(Const.ONLY_ROOT_AUTHORIZE_EL)
    @PostMapping("/create")
    fun createSysTenant(@NotNull @RequestBody sysTenantCreateInput: SysTenantCreateInput): R<SysTenantDetailView> {
        val data = sysTenantService.createSysTenant(sysTenantCreateInput)
        return R(data = data)
    }

    @PreAuthorize(Const.ONLY_ROOT_AUTHORIZE_EL)
    @PostMapping("/update")
    fun updateSysTenantById(@NotNull @RequestBody sysTenantUpdateInput: SysTenantUpdateInput): R<SysTenantDetailView> {
        val data = sysTenantService.updateSysTenantById(sysTenantUpdateInput)
        return R(data = data)
    }

    @PreAuthorize(Const.ONLY_ROOT_AUTHORIZE_EL)
    @PostMapping("/disable/{id}")
    fun disableSysTenantById(@PathVariable id: Long): R<Boolean> {
        sysTenantService.disableSysTenantById(id)
        return R.success(data = true)
    }

    @PreAuthorize(Const.ONLY_ROOT_AUTHORIZE_EL)
    @PostMapping("/enable/{id}")
    fun enableSysTenantById(@PathVariable id: Long): R<Boolean> {
        sysTenantService.enableSysTenantById(id)
        return R.success(data = true)
    }
}
