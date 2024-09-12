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

    val tenant: String
}
