package com.zhengchalei.cloud.platform.config.jimmer

import org.babyfish.jimmer.sql.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
interface TenantAware {

    val tenant: String

    val createTime: LocalDateTime

    val updateTime: LocalDateTime
}