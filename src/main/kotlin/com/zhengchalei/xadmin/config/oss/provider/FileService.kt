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

import com.zhengchalei.xadmin.modules.file.domain.FileType
import java.io.InputStream
import org.springframework.web.multipart.MultipartFile

interface FileService {
    fun getFileType(multipartFile: MultipartFile): FileType {
        return when {
            multipartFile.contentType?.contains("image") == true -> FileType.IMAGE
            multipartFile.contentType?.contains("video") == true -> FileType.VIDEO
            multipartFile.contentType?.contains("audio") == true -> FileType.AUDIO
            multipartFile.contentType?.contains("pdf") == true -> FileType.DOCUMENT
            else -> FileType.OTHER
        }
    }

    /**
     * 上传文件
     *
     * @param [multipartFile] 文件
     * @return [String]
     */
    fun uploadFile(multipartFile: MultipartFile): String

    /**
     * 获取文件
     *
     * @param [fileName]
     * @return [Pair<String, ByteArray>]
     */
    fun getFile(fileName: String): Pair<String, InputStream>
}
