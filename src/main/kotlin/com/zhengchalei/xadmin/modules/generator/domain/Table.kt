/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.generator.domain

import com.zhengchalei.xadmin.config.jimmer.entity.BaseEntity
import com.zhengchalei.xadmin.modules.sys.domain.SysPermission
import org.babyfish.jimmer.sql.*
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "gen_table")
interface Table : BaseEntity {

    /** 名称 */
    val name: String

    /** 包名 */
    val packageName: String

    /** 表前缀 */
    val tablePrefix: String?

    /** 权限id */
    @Key val tableName: String

    /** 权限 */
    @ManyToOne @JoinColumn(name = "permission_id") val permission: SysPermission?

    @IdView("permission") val permissionId: Long?

    /** 创建时间 */
    val description: String?

    @OneToMany(mappedBy = "table") val columns: List<TableColumn>

    @IdView(value = "columns") val columnIds: List<Long>
}
