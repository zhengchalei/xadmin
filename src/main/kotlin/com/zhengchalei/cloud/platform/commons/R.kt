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
        fun <T> success(data: T? = null): R<T> {
            return R(data = data)
        }

        fun <T> success(page: Page<T>): R<MutableList<T>> {
            return R(data = page.content, total = page.totalElements)
        }

        fun <T> error(errorMessage: String? = null): R<T> {
            return R(success = false, errorMessage = errorMessage)
        }

        fun <T> error(errorMessage: String? = null, code: Int): R<T> {
            return R(success = false, errorMessage = errorMessage)
        }
    }
}
