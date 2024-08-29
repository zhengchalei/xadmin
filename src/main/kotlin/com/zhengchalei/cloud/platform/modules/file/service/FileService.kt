package com.zhengchalei.cloud.platform.modules.file.service

import org.springframework.web.multipart.MultipartFile

interface FileService {
    /**
     * 上传文件
     * @param [multipartFile] 文件
     * @return [String]
     */
    fun uploadFile(multipartFile: MultipartFile): String
}
