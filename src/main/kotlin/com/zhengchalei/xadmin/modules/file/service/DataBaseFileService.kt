/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.file.service

import com.zhengchalei.xadmin.config.EmptyFileException
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
