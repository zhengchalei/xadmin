/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.sys.domain

import com.zhengchalei.xadmin.config.jimmer.entity.DataScopeEntity
import java.util.*
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_operation_log")
interface SysOperationLog : DataScopeEntity {

    val name: String

    val methodReference: String

    val operationType: OperationType

    val httpMethod: HttpMethod

    val url: String

    val ip: String

    val address: String?

    val params: String?

    val result: String?

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

annotation class Log(val value: String, val type: OperationType)

enum class OperationType {
    CREATE,
    DELETE,
    UPDATE,
    QUERY,
    OTHER,
}
