/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.domain

import com.zhengchalei.cloud.platform.config.jimmer.BaseEntity
import com.zhengchalei.cloud.platform.config.log.OperationType
import java.util.*
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_operation_log")
interface SysOperationLog : BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long

    @ManyToOne val user: SysUser?

    // 操作名称
    val name: String

    val methodReference: String

    // 操作类型
    val operationType: OperationType

    val httpMethod: HttpMethod

    val url: String

    val ip: String

    val address: String?

    val requestData: String?

    val responseData: String?

    val time: Long

    val status: Boolean

    val errorMessage: String?

    val errorStack: String?
}

enum class HttpMethod {
    GET,
    HEAD,
    POST,
    PUT,
    PATCH,
    DELETE,
    OPTIONS,
    TRACE;

    companion object {
        fun value(name: String): HttpMethod {
            return try {
                valueOf(name.uppercase(Locale.getDefault()))
            } catch (e: IllegalArgumentException) {
                throw IllegalArgumentException("Unknown HTTP method: $name", e)
            }
        }
    }
}
