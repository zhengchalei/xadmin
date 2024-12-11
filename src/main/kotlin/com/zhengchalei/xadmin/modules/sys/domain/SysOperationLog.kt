/*
Copyright 2024 [郑查磊]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
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
