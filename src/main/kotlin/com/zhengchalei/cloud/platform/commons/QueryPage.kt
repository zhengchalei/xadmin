/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.commons

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

data class QueryPage(private val currentPage: Int = 1, private val pageSize: Int = 10) {
    fun sort(): Sort = Sort.by(Sort.Direction.ASC, "id")

    fun page(): PageRequest = PageRequest.of(currentPage - 1, pageSize, sort())
}
