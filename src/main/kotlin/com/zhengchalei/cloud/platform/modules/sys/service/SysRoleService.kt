package com.zhengchalei.cloud.platform.modules.sys.service

import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.config.ServiceException
import com.zhengchalei.cloud.platform.modules.sys.domain.SysRole
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysRoleCreateInput
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysRoleDetailView
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysRolePageSpecification
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysRoleUpdateInput
import com.zhengchalei.cloud.platform.modules.sys.repository.SysRoleRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 系统角色服务
 * @author 郑查磊
 * @date 2024-08-17
 * @constructor 创建[SysRoleService]
 * @param [sysRoleRepository] 系统角色存储库
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class SysRoleService(
    private val sysRoleRepository: SysRoleRepository,
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(SysRoleService::class.java)

    /**
     * 查找系统角色通过ID
     * @param [id] ID
     * @return [SysRoleDetailView]
     */
    fun findSysRoleById(id: Long): SysRoleDetailView = this.sysRoleRepository.findDetailById(id)

    /**
     * 查找系统角色列表
     * @param [specification] 查询条件构造器
     */
    fun findSysRoleList(specification: SysRolePageSpecification) = this.sysRoleRepository.findList(specification)

    /**
     * 查找系统角色分页
     * @param [specification] 查询条件构造器
     * @param [pageable] 可分页
     */
    fun findSysRolePage(
        specification: SysRolePageSpecification,
        pageable: Pageable,
    ) = this.sysRoleRepository.findPage(specification, pageable)

    /**
     * 创造系统角色
     * @param [sysRoleCreateInput] 系统角色创建输入
     * @return [SysRoleDetailView]
     */
    fun createSysRole(sysRoleCreateInput: SysRoleCreateInput): SysRoleDetailView {
        if (sysRoleCreateInput.code == Const.ADMIN_ROLE) {
            throw ServiceException("角色不能为 ${Const.ADMIN_ROLE}")
        }
        val sysRole: SysRole = this.sysRoleRepository.insert(sysRoleCreateInput)
        return findSysRoleById(sysRole.id)
    }

    /**
     * 更新系统角色通过ID
     * @param [sysRoleUpdateInput] 系统角色更新输入
     * @return [SysRoleDetailView]
     */
    fun updateSysRoleById(sysRoleUpdateInput: SysRoleUpdateInput): SysRoleDetailView {
        val oldRole =
            this.sysRoleRepository
                .findById(sysRoleUpdateInput.id)
                .orElseThrow { ServiceException("角色不存在") }
        if (oldRole.code == Const.ADMIN_ROLE) {
            throw ServiceException("${Const.ADMIN_ROLE} 不能修改")
        }
        if (sysRoleUpdateInput.code == Const.ADMIN_ROLE) {
            throw ServiceException("${Const.ADMIN_ROLE} 不能修改")
        }
        val sysRole = this.sysRoleRepository.update(sysRoleUpdateInput)
        return findSysRoleById(sysRole.id)
    }

    /**
     * 删除系统角色通过ID
     * @param [id] ID
     */
    fun deleteSysRoleById(id: Long) {
        val sysRole = this.sysRoleRepository.findById(id).orElseThrow { ServiceException("角色不存在") }
        if (sysRole.code == Const.ADMIN_ROLE) {
            throw ServiceException("${Const.ADMIN_ROLE} 不能删除")
        }
        this.sysRoleRepository.deleteById(id)
    }
}
