/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.config.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class SysUserAuthentication(
    var id: Long,
    var username: String,
    var password: String,
    authorities: Collection<GrantedAuthority> = emptyList(),
) : UsernamePasswordAuthenticationToken(username, password, authorities)
