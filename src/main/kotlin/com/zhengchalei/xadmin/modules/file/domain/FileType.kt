/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.file.domain

import org.babyfish.jimmer.sql.EnumItem
import org.babyfish.jimmer.sql.EnumType

@EnumType(EnumType.Strategy.NAME)
enum class FileType {
    @EnumItem(name = "IMAGE") IMAGE,
    @EnumItem(name = "DOCUMENT") DOCUMENT,
    @EnumItem(name = "VIDEO") VIDEO,
    @EnumItem(name = "AUDIO") AUDIO,
    @EnumItem(name = "OTHER") OTHER,
}
