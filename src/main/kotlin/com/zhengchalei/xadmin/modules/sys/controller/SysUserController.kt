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

import cn.hutool.json.JSONObject
import cn.idev.excel.FastExcel
import com.zhengchalei.xadmin.commons.QueryPage
import com.zhengchalei.xadmin.commons.R
import com.zhengchalei.xadmin.modules.sys.domain.Gender
import com.zhengchalei.xadmin.modules.sys.domain.Log
import com.zhengchalei.xadmin.modules.sys.domain.OperationType
import com.zhengchalei.xadmin.modules.sys.domain.dto.*
import com.zhengchalei.xadmin.modules.sys.service.SysUserService
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.constraints.NotNull
import java.io.InputStream
import java.net.URLEncoder
import java.time.format.DateTimeFormatter
import org.babyfish.jimmer.client.meta.Api
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Api("sys")
@Validated
@RestController
@RequestMapping("/api/sys/user")
class SysUserController(val sysUserService: SysUserService) {
    @PreAuthorize("hasAuthority('sys:user:read') or hasAnyRole('admin')")
    @GetMapping("/id/{id}")
    fun findSysUserById(@PathVariable id: Long): R<SysUserDetailView> {
        val data = sysUserService.findSysUserById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:user:read') or hasAnyRole('admin')")
    @GetMapping("/list")
    fun findSysUserList(@NotNull specification: SysUserListSpecification): R<List<SysUserPageView>> {
        val data = sysUserService.findSysUserList(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('sys:user:read') or hasAnyRole('admin')")
    @GetMapping("/page")
    fun findSysUserPage(
        @NotNull specification: SysUserListSpecification,
        @NotNull pageable: QueryPage,
    ): R<MutableList<SysUserPageView>> {
        val data = sysUserService.findSysUserPage(specification, pageable.page())
        return R.success(data)
    }

    @GetMapping("/export")
    fun download(@NotNull specification: SysUserListSpecification, response: HttpServletResponse) {
        val list = sysUserService.findSysUserExportList(specification)
        // 文件模板
        val inputStream: InputStream = this.javaClass.classLoader.getResourceAsStream("excel/sys/用户列表.xlsx") ?: return
        // 生成文件名称
        val fileName = URLEncoder.encode("系统用户数据", "UTF-8").replace("\\+".toRegex(), "%20")
        response.contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        response.characterEncoding = "utf-8"
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''$fileName.xlsx")

        FastExcel.write(response.outputStream)
            .withTemplate(inputStream)
            .sheet()
            .doFill(
                list.map {
                    val map = JSONObject(it)
                    map["username"] = it.username
                    map["email"] = it.email
                    map["phoneNumber"] = it.phoneNumber
                    map["gender"] =
                        when (it.gender) {
                            Gender.MALE -> "男"
                            Gender.FEMALE -> "女"
                            else -> "未知"
                        }
                    map["status"] = if (it.status) "正常" else "禁用"
                    map["departmentName"] = it.department?.name
                    map["postsName"] = it.posts?.name
                    map["roleNames"] = it.roles.joinToString(",") { role -> role.name }
                    map["birthday"] = it.birthday?.format(DateTimeFormatter.ofPattern("yyyy年-MM月-dd日"))
                    map
                }
            )
    }

    @Log(value = "创建系统用户", type = OperationType.CREATE)
    @PreAuthorize("hasAuthority('sys:user:create') or hasAnyRole('admin')")
    @PostMapping("/create")
    fun createSysUser(@NotNull @RequestBody sysUserCreateInput: SysUserCreateInput): R<SysUserDetailView> {
        val data = sysUserService.createSysUser(sysUserCreateInput)
        return R(data = data)
    }

    @Log(value = "修改系统用户", type = OperationType.UPDATE)
    @PreAuthorize("hasAuthority('sys:user:edit') or hasAnyRole('admin')")
    @PostMapping("/update")
    fun updateSysUserById(@NotNull @RequestBody sysUserUpdateInput: SysUserUpdateInput): R<SysUserDetailView> {
        val data = sysUserService.updateSysUserById(sysUserUpdateInput)
        return R(data = data)
    }

    @Log(value = "删除系统用户", type = OperationType.DELETE)
    @PreAuthorize("hasAuthority('sys:user:delete') or hasAnyRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun deleteSysUserById(@NotNull @PathVariable id: Long): R<Boolean> {
        sysUserService.deleteSysUserById(id)
        return R()
    }
}
