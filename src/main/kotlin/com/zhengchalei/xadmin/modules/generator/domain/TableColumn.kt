/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.generator.domain

import com.zhengchalei.xadmin.config.jimmer.entity.BaseEntity
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "gen_table_column")
interface TableColumn : BaseEntity {

    /** 表id */
    @Key val tableId: Long

    /** 字段名 */
    @Key val columnName: String

    /** 字段描述 */
    val columnComment: String?

    /** 字段类型 */
    val columnType: String?

    /** 字段长度 */
    val columnLength: Int?

    /** 字段精度 */
    val columnPrecision: Int?

    /** 是否为空 */
    val columnNullable: Boolean?
}
