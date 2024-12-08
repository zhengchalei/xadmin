/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.config.jimmer.entity

import com.zhengchalei.xadmin.modules.sys.domain.SysUser
import java.time.LocalDateTime
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.MappedSuperclass

@MappedSuperclass
interface BaseEntity {
    val createTime: LocalDateTime

    val updateTime: LocalDateTime

    @ManyToOne @JoinColumn(name = "create_by") val createBy: SysUser?

    @IdView("createBy") val createById: Long?

    @ManyToOne @JoinColumn(name = "update_by") val updateBy: SysUser?

    @IdView("updateBy") val updateById: Long?
}
