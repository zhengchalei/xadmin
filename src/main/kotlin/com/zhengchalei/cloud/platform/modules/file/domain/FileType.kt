package com.zhengchalei.cloud.platform.modules.file.domain

import org.babyfish.jimmer.sql.EnumItem
import org.babyfish.jimmer.sql.EnumType

@EnumType(EnumType.Strategy.NAME)
enum class FileType {
    @EnumItem(name = "IMAGE")
    IMAGE,

    @EnumItem(name = "DOCUMENT")
    DOCUMENT,

    @EnumItem(name = "VIDEO")
    VIDEO,

    @EnumItem(name = "AUDIO")
    AUDIO,

    @EnumItem(name = "OTHER")
    OTHER,
}
