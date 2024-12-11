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
package com.zhengchalei.xadmin.commons.util

import org.apache.commons.lang3.StringUtils

object StrUtil {

    fun convertToPascalCase(tableName: String, tablePrefix: String? = null): String {
        if (tableName.isBlank()) return ""
        val nameWithoutPrefix =
            tablePrefix?.takeIf { it.isNotEmpty() && tableName.startsWith(it) }?.let { tableName.removePrefix(it) }
                ?: tableName
        return nameWithoutPrefix.split("_").filter { it.isNotEmpty() }.joinToString("") { StringUtils.capitalize(it) }
    }

    fun convertToCamelCase(tableName: String, tablePrefix: String? = null): String {
        if (tableName.isBlank()) return ""
        val nameWithoutPrefix =
            tablePrefix?.takeIf { it.isNotEmpty() && tableName.startsWith(it) }?.let { tableName.removePrefix(it) }
                ?: tableName
        val pascalCase =
            nameWithoutPrefix.split("_").filter { it.isNotEmpty() }.joinToString("") { StringUtils.capitalize(it) }
        return pascalCase.replaceFirstChar { it.lowercaseChar() }
    }

    fun convertToKebabCase(tableName: String, tablePrefix: String? = null): String {
        if (tableName.isBlank()) return ""
        val nameWithoutPrefix =
            tablePrefix?.takeIf { it.isNotEmpty() && tableName.startsWith(it) }?.let { tableName.removePrefix(it) }
                ?: tableName
        return nameWithoutPrefix
            .split("_")
            .filter { it.isNotEmpty() }
            .joinToString("-") { StringUtils.capitalize(it) }
            .replaceFirstChar { it.lowercaseChar() }
    }
}
