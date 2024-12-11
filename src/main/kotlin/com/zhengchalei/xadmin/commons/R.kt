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
package com.zhengchalei.xadmin.commons

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
