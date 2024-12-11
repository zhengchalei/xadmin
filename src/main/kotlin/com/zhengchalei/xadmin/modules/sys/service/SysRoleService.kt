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
package com.zhengchalei.xadmin.modules.sys.service

import com.zhengchalei.xadmin.commons.Const
import com.zhengchalei.xadmin.config.exceptions.ServiceException
import com.zhengchalei.xadmin.modules.sys.domain.SysRole
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysRoleCreateInput
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysRoleDetailView
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysRoleListSpecification
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysRoleUpdateInput
import com.zhengchalei.xadmin.modules.sys.repository.SysRoleRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 系统角色服务
 *
 * @param [sysRoleRepository] 系统角色存储库
 * @constructor 创建[SysRoleService]
 * @author 郑查磊
 * @date 2024-08-17
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class SysRoleService(private val sysRoleRepository: SysRoleRepository) {
    private val logger = org.slf4j.LoggerFactory.getLogger(SysRoleService::class.java)

    /**
     * 查找系统角色通过ID
     *
     * @param [id] ID
     * @return [SysRoleDetailView]
     */
    fun findSysRoleById(id: Long): SysRoleDetailView = this.sysRoleRepository.findDetailById(id)

    /**
     * 查找系统角色列表
     *
     * @param [specification] 查询条件构造器
     */
    fun findSysRoleList(specification: SysRoleListSpecification) = this.sysRoleRepository.findList(specification)

    /**
     * 查找系统角色分页
     *
     * @param [specification] 查询条件构造器
     * @param [pageable] 可分页
     */
    fun findSysRolePage(specification: SysRoleListSpecification, pageable: Pageable) =
        this.sysRoleRepository.findPage(specification, pageable)

    /**
     * 创造系统角色
     *
     * @param [sysRoleCreateInput] 系统角色创建输入
     * @return [SysRoleDetailView]
     */
    fun createSysRole(sysRoleCreateInput: SysRoleCreateInput): SysRoleDetailView {
        if (sysRoleCreateInput.code == Const.AdminRole) {
            throw ServiceException("角色不能为 ${Const.AdminRole}")
        }
        val sysRole: SysRole = this.sysRoleRepository.insert(sysRoleCreateInput)
        return findSysRoleById(sysRole.id)
    }

    /**
     * 更新系统角色通过ID
     *
     * @param [sysRoleUpdateInput] 系统角色更新输入
     * @return [SysRoleDetailView]
     */
    fun updateSysRoleById(sysRoleUpdateInput: SysRoleUpdateInput): SysRoleDetailView {
        val oldRole = this.sysRoleRepository.findById(sysRoleUpdateInput.id).orElseThrow { ServiceException("角色不存在") }
        if (oldRole.code == Const.AdminRole) {
            throw ServiceException("${Const.AdminRole} 不能修改")
        }
        if (sysRoleUpdateInput.code == Const.AdminRole) {
            throw ServiceException("${Const.AdminRole} 不能修改")
        }
        val sysRole = this.sysRoleRepository.update(sysRoleUpdateInput)
        return findSysRoleById(sysRole.id)
    }

    /**
     * 删除系统角色通过ID
     *
     * @param [id] ID
     */
    fun deleteSysRoleById(id: Long) {
        val sysRole = this.sysRoleRepository.findById(id).orElseThrow { ServiceException("角色不存在") }
        if (sysRole.code == Const.AdminRole) {
            throw ServiceException("${Const.AdminRole} 不能删除")
        }
        this.sysRoleRepository.deleteById(id)
    }
}
