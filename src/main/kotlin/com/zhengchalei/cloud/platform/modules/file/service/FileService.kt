package com.zhengchalei.cloud.platform.modules.file.service

import com.zhengchalei.cloud.platform.modules.file.domain.FileType
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

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
     * @param [multipartFile] 文件
     * @return [String]
     */
    fun uploadFile(multipartFile: MultipartFile): String

    /**
     * 获取文件
     * @param [fileName]
     * @return [Pair<String, ByteArray>]
     */
    fun getFile(fileName: String): Pair<String, InputStream>
}
