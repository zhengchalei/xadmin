package com.zhengchalei.cloud.platform.modules.sys.service

import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.config.ServiceException
import com.zhengchalei.cloud.platform.modules.sys.domain.SysUser
import com.zhengchalei.cloud.platform.modules.sys.domain.by
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.*
import com.zhengchalei.cloud.platform.modules.sys.repository.SysUserRepository
import org.babyfish.jimmer.kt.new
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 系统用户服务
 *
 * @param [sysUserRepository] 系统用户存储库
 * @constructor 创建[SysUserService]
 * @author 郑查磊
 * @date 2024-08-17
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class SysUserService(
    private val sysUserRepository: SysUserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jdbcTemplate: JdbcTemplate,
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(SysUserService::class.java)

    /**
     * 查找系统用户通过ID
     *
     * @param [id] ID
     * @return [SysUserDetailView]
     */
    fun findSysUserById(id: Long): SysUserDetailView = this.sysUserRepository.findDetailById(id)

    /**
     * 查找系统用户列表
     *
     * @param [specification] 查询条件构造器
     */
    fun findSysUserList(specification: SysUserPageSpecification) =
        this.sysUserRepository.findList(specification)

    /**
     * 查找系统用户分页
     *
     * @param [specification] 查询条件构造器
     * @param [pageable] 可分页
     */
    fun findSysUserPage(
        specification: SysUserPageSpecification,
        pageable: Pageable,
    ): Page<SysUserPageView> {
        // 此处为了查询能够查询到子节点
        val sql =
            """
            WITH RECURSIVE DepartmentHierarchy AS (
                SELECT id FROM sys_department WHERE id = ?
                UNION ALL
                SELECT d.id FROM sys_department d
                INNER JOIN DepartmentHierarchy dh ON d.parent_id = dh.id
            )
            SELECT id FROM DepartmentHierarchy
        """
        if (specification.departmentId != null) {
            val sysDepartmentIds =
                jdbcTemplate.queryForList(sql, Long::class.java, specification.departmentId)
            return this.sysUserRepository.findPage(specification, pageable, sysDepartmentIds)
        }
        return this.sysUserRepository.findPage(specification, pageable)
    }

    /**
     * 创建系统用户
     *
     * @param [sysUserCreateInput] 系统用户创建输入
     * @return [SysUserDetailView]
     */
    fun createSysUser(sysUserCreateInput: SysUserCreateInput): SysUserDetailView {
        if (
            sysUserCreateInput.username == Const.AdminUser ||
                sysUserCreateInput.username == Const.Root
        ) {
            throw ServiceException("用户名不能为 ${Const.AdminUser}")
        }
        // TODO 集成邮件后, 这里密码应该由邮件发送
        val newSysUser =
            new(SysUser::class).by(sysUserCreateInput.toEntity()) {
                password = passwordEncoder.encode(Const.DefaultPassword)
            }
        val sysUser: SysUser = this.sysUserRepository.insert(newSysUser)
        return findSysUserById(sysUser.id)
    }

    /**
     * 更新系统用户分页通过ID
     *
     * @param [sysUserUpdateInput] 系统用户更新输入
     * @return [SysUserDetailView]
     */
    fun updateSysUserById(sysUserUpdateInput: SysUserUpdateInput): SysUserDetailView {
        val oldUser =
            this.sysUserRepository.findById(sysUserUpdateInput.id).orElseThrow {
                throw ServiceException("用户不存在")
            }
        if (oldUser.username == Const.AdminUser || oldUser.username == Const.Root) {
            throw ServiceException("${Const.AdminUser} 不能修改用户名")
        }
        val sysUser = this.sysUserRepository.update(sysUserUpdateInput)
        return findSysUserById(sysUser.id)
    }

    /**
     * 删除系统用户分页通过ID
     *
     * @param [id] ID
     */
    fun deleteSysUserById(id: Long) {
        val sysUser =
            this.sysUserRepository.findById(id).orElseThrow { throw ServiceException("用户不存在") }
        if (sysUser.username == Const.AdminUser || sysUser.username == Const.Root) {
            throw ServiceException("${sysUser.username} 不能删除")
        }
        this.sysUserRepository.deleteById(id)
    }

    /**
     * 当前用户信息
     *
     * @return [SysUser]
     */
    fun currentUserInfo(): SysUser = this.sysUserRepository.currentUserInfo()

    /**
     * 当前用户 ID
     *
     * @return [Long]
     */
    fun currentUserId(): Long = this.sysUserRepository.currentUserId()
}
