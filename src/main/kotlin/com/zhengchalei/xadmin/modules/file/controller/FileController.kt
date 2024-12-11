/*
Copyright 2024 [郑查磊]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.zhengchalei.xadmin.modules.file.controller

import com.zhengchalei.xadmin.commons.R
import com.zhengchalei.xadmin.config.oss.provider.FileService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.io.InputStream
import java.io.OutputStream
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/external/file")
class FileController(val fileService: FileService) {
    /**
     * 上传文件
     *
     * @param [file] 文件
     * @return [R<String>]
     */
    @PostMapping(path = ["/upload"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadFile(@RequestParam("file") file: MultipartFile, httpServletRequest: HttpServletRequest): R<String> {
        val uuid = this.fileService.uploadFile(file)
        // 获取请求的来源域名
        val host = httpServletRequest.getHeader("Host")
        // 获取协议
        val protocol = httpServletRequest.scheme
        // 防止第三方对接出现各种问题
        return R.success(data = "$protocol://$host/api/external/file/download/$uuid")
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
