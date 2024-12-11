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
package com.zhengchalei.xadmin.config.oss.provider

import com.zhengchalei.xadmin.config.oss.FileConfigurationProperties
import java.io.ByteArrayInputStream
import java.io.File
import java.util.*
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@ConditionalOnProperty(prefix = "file", name = ["storage"], havingValue = "local")
@Service
class LocalFileService(private val fileConfigProperties: FileConfigurationProperties) : FileService {
    /**
     * 上传图片
     *
     * @param [multipartFile] 多部分文件
     * @return [String]
     */
    override fun uploadFile(multipartFile: MultipartFile): String {
        val localConfig = fileConfigProperties.local

        if (localConfig.storagePath.isBlank()) {
            throw RuntimeException("本地存储路径未配置")
        }
        if (multipartFile.isEmpty) {
            throw RuntimeException("文件为空")
        }
        if (multipartFile.size > 1024 * 1024 * 10) {
            throw RuntimeException("文件大小不能超过10M")
        }
        // 判断存储位置是否存在文件夹, 否则创建文件夹
        if (!File(localConfig.storagePath).exists()) {
            File(localConfig.storagePath).mkdirs()
        }
        val fileName = UUID.randomUUID().toString() + "." + multipartFile.originalFilename?.substringAfterLast(".")
        val filePath = localConfig.storagePath + "/" + fileName
        try {
            multipartFile.transferTo(File(filePath))
        } catch (e: Exception) {
            throw RuntimeException("文件上传失败")
        }
        return fileName
    }

    /**
     * 获取文件
     *
     * @param [fileName]
     * @return [Pair<String, ByteArray>]
     */
    override fun getFile(fileName: String): Pair<String, ByteArrayInputStream> {
        val localConfig = fileConfigProperties.local
        if (localConfig.storagePath.isBlank()) {
            throw RuntimeException("本地存储路径未配置")
        }
        val filePath = localConfig.storagePath + "/" + fileName
        val file = File(filePath)
        if (!file.exists()) {
            throw RuntimeException("文件不存在")
        }
        return fileName to ByteArrayInputStream(file.readBytes())
    }
}
