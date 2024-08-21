package com.zhengchalei.cloud.platform.modules.sys.service

import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.modules.sys.domain.SysUser
import com.zhengchalei.cloud.platform.modules.sys.domain.by
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysUserCreateInput
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysUserDetailView
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysUserPageSpecification
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysUserUpdateInput
import com.zhengchalei.cloud.platform.modules.sys.repository.SysUserRepository
import org.babyfish.jimmer.kt.new
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 系统用户服务
 * @author 郑查磊
 * @date 2024-08-17
 * @constructor 创建[SysUserService]
 * @param [sysUserRepository] 系统用户存储库
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class SysUserService(
    private val sysUserRepository: SysUserRepository
) {

    private val logger = org.slf4j.LoggerFactory.getLogger(SysUserService::class.java)

    /**
     * 查找系统用户通过ID
     * @param [id] ID
     * @return [SysUserDetailView]
     */
    fun findSysUserById(id: Long): SysUserDetailView {
        return this.sysUserRepository.findDetailById(id)
    }

    /**
     * 查找系统用户列表
     * @param [specification] 查询条件构造器
     */
    fun findSysUserList(specification: SysUserPageSpecification) = this.sysUserRepository.findList(specification)

    /**
     * 查找系统用户分页
     * @param [specification] 查询条件构造器
     * @param [pageable] 可分页
     */
    fun findSysUserPage(specification: SysUserPageSpecification, pageable: Pageable) =
        this.sysUserRepository.findPage(specification, pageable)

    /**
     * 创建系统用户
     * @param [sysUserCreateInput] 系统用户创建输入
     * @return [SysUserDetailView]
     */
    fun createSysUser(sysUserCreateInput: SysUserCreateInput): SysUserDetailView {
        if (sysUserCreateInput.username == Const.ADMIN_USER || sysUserCreateInput.username == Const.SuperAdmin) {
            throw IllegalArgumentException("用户名不能为 ${Const.ADMIN_USER}")
        }
        // TODO 集成邮件后, 这里密码应该由邮件发送
        val newSysUser = new(SysUser::class).by(sysUserCreateInput.toEntity()) {
            password = Const.DEFAULT_PASSWORD
        }
        val sysUser: SysUser = this.sysUserRepository.insert(newSysUser)
        return findSysUserById(sysUser.id)
    }

    /**
     * 更新系统用户分页通过ID
     * @param [sysUserUpdateInput] 系统用户更新输入
     * @return [SysUserDetailView]
     */
    fun updateSysUserPageById(sysUserUpdateInput: SysUserUpdateInput): SysUserDetailView {
        val oldUser = this.sysUserRepository.findById(sysUserUpdateInput.id)
            .orElseThrow { throw IllegalArgumentException("用户不存在") }
        if (oldUser.username == Const.ADMIN_USER || oldUser.username == Const.SuperAdmin) {
            throw IllegalArgumentException("${Const.ADMIN_USER} 不能修改用户名")
        }
        val sysUser = this.sysUserRepository.update(sysUserUpdateInput)
        return findSysUserById(sysUser.id)
    }

    /**
     * 删除系统用户分页通过ID
     * @param [id] ID
     */
    fun deleteSysUserPageById(id: Long) {
        val sysUser = this.sysUserRepository.findById(id).orElseThrow { throw IllegalArgumentException("用户不存在") }
        if (sysUser.username == Const.ADMIN_USER || sysUser.username == Const.SuperAdmin) {
            throw IllegalArgumentException("${sysUser.username} 不能删除")
        }
        this.sysUserRepository.deleteById(id)
    }

    /**
     * 当前用户信息
     * @return [SysUser]
     */
    fun currentUserInfo(): SysUser {
        return this.sysUserRepository.currentUserInfo()
    }

}