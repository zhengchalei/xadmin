/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.config

import jakarta.servlet.http.HttpServletRequest
import java.net.InetAddress
import org.slf4j.LoggerFactory

object IPUtil {

    private val log = LoggerFactory.getLogger(IPUtil::class.java)

    fun getIpAddress(request: HttpServletRequest): String {
        try {
            var ipAddress = request.getHeader("x-forwarded-for")
            if (ipAddress.isNullOrEmpty() || "unknown".equals(ipAddress, ignoreCase = true)) {
                ipAddress = request.getHeader("Proxy-Client-IP")
            }
            if (ipAddress.isNullOrEmpty() || "unknown".equals(ipAddress, ignoreCase = true)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP")
            }
            if (ipAddress.isNullOrEmpty() || "unknown".equals(ipAddress, ignoreCase = true)) {
                ipAddress = request.remoteAddr
                if (ipAddress == "0:0:0:0:0:0:0:1") {
                    // 根据网卡取本机配置的IP
                    ipAddress = InetAddress.getLocalHost().hostAddress
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP，多个IP按照','分割
            if (ipAddress.isNotEmpty() && ipAddress.length > 15 && ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","))
            }
            return ipAddress
        } catch (e: Exception) {
            log.error("获取ip地址失败", e)
        }
        return ""
    }
}
