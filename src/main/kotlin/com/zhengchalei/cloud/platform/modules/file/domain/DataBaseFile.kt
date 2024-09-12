/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.file.domain

import com.zhengchalei.cloud.platform.config.jimmer.TenantAware
import java.io.InputStream
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_file")
interface DataBaseFile : TenantAware {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long

    val uid: String

    val originalName: String

    val type: FileType

    val fileData: InputStream
}
