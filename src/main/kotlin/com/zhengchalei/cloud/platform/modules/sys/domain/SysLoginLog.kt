/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.domain

import java.time.LocalDateTime
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_login_log")
interface SysLoginLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long

    val username: String

    val password: String

    val ip: String

    val address: String?

    val loginTime: LocalDateTime

    val status: Boolean

    val errorMessage: String?

    @ManyToOne @JoinColumn(name = "user_id") val sysUser: SysUser?

}
