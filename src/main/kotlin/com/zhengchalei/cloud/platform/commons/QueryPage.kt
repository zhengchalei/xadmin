package com.zhengchalei.cloud.platform.commons

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class QueryPage(val currentPage: Int = 1, val pageSize: Int = 10) {
    fun sort(): Sort = Sort.by(Sort.Direction.ASC, "id")

    fun page(): PageRequest = PageRequest.of(currentPage - 1, pageSize, sort())
}
