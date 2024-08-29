package com.zhengchalei.cloud.platform.modules.file.controller

import com.zhengchalei.cloud.platform.commons.R
import com.zhengchalei.cloud.platform.modules.file.service.FileService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/external/file")
class FileController(
    val fileService: FileService,
) {
    /**
     * 上传文件
     * @param [file] 文件
     * @return [R<String>]
     */
    @PostMapping(path = ["/upload/file"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadFile(
        @RequestParam("file") file: MultipartFile,
    ): R<String> {
        val uploadImage = this.fileService.uploadFile(file)
        return R.success(data = uploadImage)
    }
}
