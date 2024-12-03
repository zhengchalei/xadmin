/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.file.service

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
