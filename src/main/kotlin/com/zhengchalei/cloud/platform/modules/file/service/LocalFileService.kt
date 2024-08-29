package com.zhengchalei.cloud.platform.modules.file.service

import com.zhengchalei.cloud.platform.config.properties.FileConfigurationProperties
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.File
import java.util.*

@ConditionalOnProperty(prefix = "file", name = ["storage"], havingValue = "local")
@Service
class LocalFileService(
    private val fileConfigProperties: FileConfigurationProperties,
) : FileService {
    /**
     * 上传图片
     * @param [multipartFile] 多部分文件
     * @return [String]
     */
    override fun uploadFile(multipartFile: MultipartFile): String {
        val localConfig = fileConfigProperties.getConfig()

        if (localConfig is FileConfigurationProperties.LocalStorageConfigurationProperties) {
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
            val fileName =
                UUID.randomUUID().toString() + "." + multipartFile.originalFilename?.substringAfterLast(".")
            val filePath = localConfig.storagePath + "/" + fileName
            try {
                multipartFile.transferTo(File(filePath))
            } catch (e: Exception) {
                throw RuntimeException("文件上传失败")
            }
            return fileName
        }
        throw RuntimeException("配置错误")
    }

    /**
     * 获取文件
     * @param [fileName]
     * @return [Pair<String, ByteArray>]
     */
    override fun getFile(fileName: String): Pair<String, ByteArrayInputStream> {
        val localConfig = fileConfigProperties.getConfig()

        if (localConfig is FileConfigurationProperties.LocalStorageConfigurationProperties) {
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
        throw RuntimeException("配置错误")
    }
}
