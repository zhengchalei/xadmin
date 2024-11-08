/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.domain

import com.zhengchalei.cloud.platform.config.jimmer.BaseEntity
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_role")
interface SysRole : BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long

    val name: String

    val code: String

    val description: String?

    @ManyToMany
    @JoinTable(
        name = "sys_role_permission",
        joinColumnName = "role_id",
        inverseJoinColumnName = "permission_id",
    )
    val permissions: List<SysPermission>

    @ManyToMany(mappedBy = "roles") val users: List<SysUser>

    @IdView("permissions") val permissionIds: List<Long>

    @IdView("users") val userIds: List<Long>
}
