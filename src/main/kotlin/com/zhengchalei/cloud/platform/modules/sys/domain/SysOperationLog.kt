/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.domain

import com.zhengchalei.cloud.platform.config.jimmer.BaseEntity
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_operation_log")
interface SysOperationLog : BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long

    @ManyToOne val user: SysUser?

    // 操作名称
    val name: String

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
