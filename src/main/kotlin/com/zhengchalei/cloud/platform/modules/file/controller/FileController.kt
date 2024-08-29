package com.zhengchalei.cloud.platform.modules.file.controller

import com.zhengchalei.cloud.platform.commons.R
import com.zhengchalei.cloud.platform.modules.file.service.LocalFileService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/external/file")
class FileController(
    val fileService: LocalFileService,
) {
    /**
     * 上传图片
     * @return [String]
     */
    @PostMapping(path = ["/upload/image"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadImage(
        @RequestParam("file") file: MultipartFile,
    ): R<String> {
        val uploadImage = this.fileService.uploadImage(file)
        return R.success(data = uploadImage)
    }
}