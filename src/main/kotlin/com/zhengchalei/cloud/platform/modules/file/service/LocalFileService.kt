package com.zhengchalei.cloud.platform.modules.file.service

import com.zhengchalei.cloud.platform.config.properties.FileConfigurationProperties
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Service
class LocalFileService(
    private val fileConfigProperties: FileConfigurationProperties,
) {
    fun uploadImage(multipartFile: MultipartFile): String {
        val localConfig = fileConfigProperties.localConfig
        if (localConfig.path.isBlank()) {
            throw RuntimeException("本地存储路径未配置")
        }
        if (multipartFile.isEmpty) {
            throw RuntimeException("文件为空")
        }
        if (multipartFile.size > 1024 * 1024 * 10) {
            throw RuntimeException("文件大小不能超过10M")
        }
        // 判断存储位置是否存在文件夹, 否则创建文件夹
        if (!File(localConfig.path).exists()) {
            File(localConfig.path).mkdirs()
        }
        val fileName = System.currentTimeMillis().toString() + "." + multipartFile.originalFilename?.substringAfterLast(".")
        val filePath = localConfig.path + "/" + fileName
        try {
            multipartFile.transferTo(File(filePath))
        } catch (e: Exception) {
            throw RuntimeException("文件上传失败")
        }
        return fileName
    }
}
