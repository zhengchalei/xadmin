/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.config.security.provider

import com.zhengchalei.xadmin.config.security.authentication.SysUserAuthentication
import org.springframework.security.core.Authentication

interface AuthTokenProvider {

    fun createToken(authentication: SysUserAuthentication): String

    fun validateToken(token: String): Boolean

    fun getAuthentication(token: String): Authentication

    fun logout(token: String)
}
