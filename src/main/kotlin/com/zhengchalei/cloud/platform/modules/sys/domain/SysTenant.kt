package com.zhengchalei.cloud.platform.modules.sys.domain

import java.time.LocalDateTime
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_tenant")
interface SysTenant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long

    val name: String

    val code: String

    val status: Boolean

    val description: String?

    val contactName: String?

    val contactPhone: String?

    val domain: String?

    val logo: String?

    val address: String?

    val createTime: LocalDateTime

    val updateTime: LocalDateTime
}
