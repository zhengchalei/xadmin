package com.zhengchalei.cloud.platform.modules.sys.domain

import com.zhengchalei.cloud.platform.config.jimmer.TenantAware
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_log")
interface SysLog : TenantAware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    @ManyToOne
    val user: SysUser?

    // 操作名称
    val name: String

    @ManyToOne
    val department: SysDepartment?

    val method: MethodType

    val httpMethod: HttpMethodType

    val url: String

    val ip: String

    val address: String?

    val requestData: String?

    val responseData: String?

    val time: Long

    val status: Boolean

    val errorStack: String?
}

enum class MethodType {
    CREATE,
    DELETE,
    UPDATE,
    QUERY,
    OTHER,
}

enum class HttpMethodType {
    GET,
    POST,
    PUT,
    DELETE,
    HEAD,
    OPTIONS,
    PATCH,
    TRACE,
}
