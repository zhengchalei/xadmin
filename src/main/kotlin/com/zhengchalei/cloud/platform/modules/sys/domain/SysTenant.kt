package com.zhengchalei.cloud.platform.modules.sys.domain

import org.babyfish.jimmer.sql.*
import java.time.LocalDateTime

@Entity
@Table(name = "sys_tenant")
interface SysTenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    val name: String

    val code: String

    val status: Boolean

    val description: String?

    val contactName: String?

    val contactPhoneNumber: String?

    val domain: String?

    val logo: String?

    val address: String?

    val createTime: LocalDateTime

    val updateTime: LocalDateTime
}