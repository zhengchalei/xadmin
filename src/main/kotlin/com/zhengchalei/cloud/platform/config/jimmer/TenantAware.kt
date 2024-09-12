/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.config.jimmer

import java.time.LocalDateTime
import org.babyfish.jimmer.sql.MappedSuperclass

@MappedSuperclass
interface TenantAware {
    val tenant: String

    val createTime: LocalDateTime

    val updateTime: LocalDateTime
}
