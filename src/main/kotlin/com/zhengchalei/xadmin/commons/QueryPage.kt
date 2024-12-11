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

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

data class QueryPage(private val currentPage: Int = 1, private val pageSize: Int = 10) {
    fun sort(): Sort = Sort.by(Sort.Direction.ASC, "id")

    fun page(): PageRequest = PageRequest.of(currentPage - 1, pageSize, sort())
}
