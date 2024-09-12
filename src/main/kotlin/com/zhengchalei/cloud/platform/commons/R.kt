/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.commons

import org.springframework.data.domain.Page

data class R<T>(
    // 数据载体
    val data: T? = null,
    val success: Boolean = true,
    val errorMessage: String? = null,
    val errorCode: Int = 0,
    val total: Long = 0L,
) {
    companion object {
        fun <T> success(data: T? = null): R<T> = R(data = data)

        fun <T> success(page: Page<T>): R<MutableList<T>> = R(data = page.content, total = page.totalElements)

        fun <T> error(errorMessage: String? = null): R<T> = R(success = false, errorMessage = errorMessage)

        fun <T> error(errorMessage: String? = null, code: Int): R<T> = R(success = false, errorMessage = errorMessage)
    }
}
