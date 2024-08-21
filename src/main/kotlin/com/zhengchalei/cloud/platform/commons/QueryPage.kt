package com.zhengchalei.cloud.platform.commons

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class QueryPage(
    val currentPage: Int = 1,
    val pageSize: Int = 10,
    val sortDirection: Sort.Direction = Sort.Direction.ASC,
    val sortProperty: String = "id",
) {

    fun sort(): Sort {
        return Sort.by(sortDirection, sortProperty)
    }

    fun page(): PageRequest {
        return PageRequest.of(currentPage - 1, pageSize, sort())
    }
}