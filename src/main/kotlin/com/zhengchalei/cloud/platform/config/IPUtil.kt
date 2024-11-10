package com.zhengchalei.cloud.platform.config

import com.zhengchalei.cloud.platform.commons.Const
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import java.net.InetAddress

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