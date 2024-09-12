/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.domain

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_permission")
interface SysPermission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long

    val name: String

    val code: String

    val description: String?

    @ManyToOne val parent: SysPermission?

    @OneToMany(mappedBy = "parent") val children: List<SysPermission>

    @IdView("children") val childrenIds: List<Long>

    @ManyToMany(mappedBy = "permissions") val roles: List<SysRole>
}
