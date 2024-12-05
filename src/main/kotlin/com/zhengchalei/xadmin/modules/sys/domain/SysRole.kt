/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.sys.domain

import com.zhengchalei.xadmin.config.jimmer.BaseEntity
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_role")
interface SysRole : BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long

    val name: String

    val code: String

    val description: String?

    @ManyToMany
    @JoinTable(name = "sys_role_permission", joinColumnName = "role_id", inverseJoinColumnName = "permission_id")
    val permissions: List<SysPermission>

    @ManyToMany(mappedBy = "roles") val users: List<SysUser>

    @IdView("permissions") val permissionIds: List<Long>

    @IdView("users") val userIds: List<Long>

    val dataScope: DataScope

    @ManyToMany
    @JoinTable(name = "sys_role_department_data_scope", joinColumnName = "role_id", inverseJoinColumnName = "department_id")
    val dataScopeDepartments: List<SysDepartment>

    @IdView("dataScopeDepartments")
    val dataScopeDepartmentIds: List<Long>
}

/**
 * 定义数据可见范围。
 * 此枚举类用于表示用户可以访问的数据范围，不同的枚举值代表不同的数据可见级别。
 *
 * @see EnumType 该注解用于指定持久化枚举类型的策略，使用枚举常量的名称。
 */
@EnumType(EnumType.Strategy.NAME)
enum class DataScope {
    /**
     * 可以访问所有数据。
     */
    ALL,

    /**
     * 只能访问自己的数据。
     */
    SELF,

    /**
     * 本部门数据权限
     */
    DEPARTMENT,

    /**
     * 可以访问子部门的数据。
     */
    DEPARTMENT_AND_SUB_DEPARTMENT,

    /**
     * 自定义数据范围。
     */
    CUSTOM;

    companion object {
        /**
         * 根据名称获取对应的 DataScope 枚举值。
         *
         * @param name 枚举值的名称
         * @return 对应的 DataScope 枚举值
         * @throws IllegalArgumentException 如果没有找到对应的枚举值
         */
        fun value(name: String): DataScope {
            return entries.find { it.name == name } ?: throw IllegalArgumentException("不存在的数据权限类型: $name")
        }
    }
}
