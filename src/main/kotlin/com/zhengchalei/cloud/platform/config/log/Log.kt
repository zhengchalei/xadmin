package com.zhengchalei.cloud.platform.config.log


annotation class Log(
    val value: String,
    val type: OperationType,
)

enum class OperationType {
    CREATE,
    DELETE,
    UPDATE,
    QUERY,
    OTHER,
}