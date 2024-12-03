/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.file.repository

import com.zhengchalei.xadmin.modules.file.domain.DataBaseFile
import com.zhengchalei.xadmin.modules.file.domain.uid
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.sql.kt.ast.expression.eq

interface DataBaseFileRepository : KRepository<DataBaseFile, Long> {
    fun findByUid(uid: String) =
        sql.createQuery(DataBaseFile::class) {
                where(table.uid eq uid)
                select(table)
            }
            .fetchOne()
}
