/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.file.domain

import com.zhengchalei.xadmin.config.jimmer.entity.BaseEntity
import java.io.InputStream
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_file")
interface DataBaseFile : BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long

    val uid: String

    val originalName: String

    val type: FileType

    val fileData: InputStream
}
