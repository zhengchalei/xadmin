package com.zhengchalei.cloud.platform.modules.file.controller

import com.zhengchalei.cloud.platform.commons.R
import com.zhengchalei.cloud.platform.modules.file.service.FileService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.io.OutputStream

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
    @PostMapping(path = ["/upload"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadFile(
        @RequestParam("file") file: MultipartFile,
    ): R<String> {
        val uuid = this.fileService.uploadFile(file)
        return R.success(data = "/api/external/file/download/$uuid")
    }

    // 下载文件

    @GetMapping("/download/{uuid}")
    fun downloadFile(
        @PathVariable(name = "uuid") uuid: String,
        request: HttpServletRequest,
        response: HttpServletResponse,
    ) {
        val data = this.fileService.getFile(uuid)
        writeFileToResponse(data.first, data.second, request, response)
    }

    fun writeFileToResponse(
        fileName: String,
        stream: InputStream,
        request: HttpServletRequest,
        response: HttpServletResponse,
    ) {
        val readAllBytes = stream.readAllBytes()
        // 设置响应头
        response.contentType = "application/octet-stream"
        response.setHeader("Content-Disposition", "attachment; filename=\"$fileName\"")
        response.setContentLength(readAllBytes.size)
        // 获取 ServletOutputStream 对象
        val servletOutputStream: OutputStream = response.outputStream
        // 直接写入数据
        servletOutputStream.write(readAllBytes)
        servletOutputStream.flush() // 确保数据被完全写出
    }
}
