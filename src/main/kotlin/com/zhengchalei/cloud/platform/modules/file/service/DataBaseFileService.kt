package com.zhengchalei.cloud.platform.modules.file.service

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@ConditionalOnProperty(prefix = "file", name = ["storage"], havingValue = "database")
@Service
class DataBaseFileService : FileService {
    /**
     * 上传图片
     * @param [multipartFile] 文件
     * @return [String]
     */
    override fun uploadFile(multipartFile: MultipartFile): String {
        return ""
    }
}
