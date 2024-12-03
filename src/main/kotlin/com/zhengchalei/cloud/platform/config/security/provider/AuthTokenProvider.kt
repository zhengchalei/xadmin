/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.config.security.provider

import com.zhengchalei.cloud.platform.config.security.SysUserAuthentication
import org.springframework.security.core.Authentication

interface AuthTokenProvider {

    fun createToken(authentication: SysUserAuthentication): String

    fun validateToken(token: String): Boolean

    fun getAuthentication(token: String): Authentication

    fun logout(token: String)
}
