package com.zhengchalei.cloud.platform.config.jimmer

import java.time.LocalDateTime
import org.babyfish.jimmer.sql.MappedSuperclass

@MappedSuperclass
interface TenantAware {
    val tenant: String

    val createTime: LocalDateTime

    val updateTime: LocalDateTime
}
