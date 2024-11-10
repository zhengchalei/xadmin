/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
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
