/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.commons

object Const {
    const val ONLY_ROOT_AUTHORIZE_EL = "authentication.principal.username == 'root'"

    const val AdminUser = "admin"

    const val AdminRole = "admin"

    const val DefaultPassword = "123456"

    const val SecurityRolePrifix = "ROLE_"

    // env
    const val ENV_PROD = "prod"
    const val ENV_DEV = "dev"
    const val ENV_TEST = "test"
}
