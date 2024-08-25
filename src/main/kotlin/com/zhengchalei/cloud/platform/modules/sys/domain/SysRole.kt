package com.zhengchalei.cloud.platform.modules.sys.domain

import com.zhengchalei.cloud.platform.config.jimmer.TenantAware
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_role")
interface SysRole : TenantAware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

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

    @ManyToMany(mappedBy = "roles")
    val users: List<SysUser>

    @IdView("permissions")
    val permissionIds: List<Long>

    @IdView("users")
    val userIds: List<Long>
}
