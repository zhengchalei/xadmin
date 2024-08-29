package com.zhengchalei.cloud.platform.modules.file.repository

import com.zhengchalei.cloud.platform.modules.file.domain.DataBaseFile
import com.zhengchalei.cloud.platform.modules.file.domain.uid
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.sql.kt.ast.expression.eq

interface DataBaseFileRepository : KRepository<DataBaseFile, Long> {
    fun findByUid(uid: String) =
        sql.createQuery(DataBaseFile::class) {
            where(table.uid eq uid)
            select(table)
        }.fetchOne()
}
