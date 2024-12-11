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

import com.zhengchalei.xadmin.config.exceptions.EmptyFileException
import com.zhengchalei.xadmin.modules.file.domain.DataBaseFile
import com.zhengchalei.xadmin.modules.file.domain.by
import com.zhengchalei.xadmin.modules.file.repository.DataBaseFileRepository
import java.io.ByteArrayInputStream
import java.util.*
import org.babyfish.jimmer.kt.new
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@ConditionalOnProperty(prefix = "file", name = ["storage"], havingValue = "database")
@Service
class DataBaseFileService(private val dataBaseFileRepository: DataBaseFileRepository) : FileService {
    /**
     * 上传图片
     *
     * @param [multipartFile] 文件
     * @return [String]
     */
    override fun uploadFile(multipartFile: MultipartFile): String {
        if (multipartFile.size > 0) {
            val baseFile =
                new(DataBaseFile::class).by {
                    this.uid =
                        UUID.randomUUID().toString() + "." + multipartFile.originalFilename?.substringAfterLast(".")
                    this.originalName = multipartFile.originalFilename ?: UUID.randomUUID().toString()
                    this.type = getFileType(multipartFile)
                    this.fileData = ByteArrayInputStream(multipartFile.bytes)
                }
            dataBaseFileRepository.insert(baseFile)
            return baseFile.uid
        }
        throw EmptyFileException("文件不能为空")
    }

    /**
     * 获取文件
     *
     * @param [fileName]
     * @return [Pair<String, ByteArray>]
     */
    override fun getFile(fileName: String): Pair<String, ByteArrayInputStream> {
        val dataBaseFile = dataBaseFileRepository.findByUid(fileName)
        return dataBaseFile.originalName to ByteArrayInputStream(dataBaseFile.fileData.readAllBytes())
    }
}
