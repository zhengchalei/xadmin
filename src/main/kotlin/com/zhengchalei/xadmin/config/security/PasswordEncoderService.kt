/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.config.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoderService : BCryptPasswordEncoder(16) {
    override fun encode(rawPassword: CharSequence): String {
        return super.encode(rawPassword)
    }

    override fun matches(rawPassword: CharSequence, encodedPassword: String): Boolean {
        return super.matches(rawPassword, encodedPassword)
    }
}
