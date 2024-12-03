/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.sys.repository

import com.zhengchalei.xadmin.modules.sys.domain.SysOperationLog
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysOperationLogDetailView
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysOperationLogListSpecification
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysOperationLogPageView
import com.zhengchalei.xadmin.modules.sys.domain.id
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SysOperationLogRepository : KRepository<SysOperationLog, Long> {
    fun findDetailById(id: Long) =
        sql.createQuery(SysOperationLog::class) {
                where(table.id eq id)
                select(table.fetch(SysOperationLogDetailView::class))
            }
            .fetchOne()

    fun findPage(specification: SysOperationLogListSpecification, pageable: Pageable): Page<SysOperationLogPageView> =
        sql.createQuery(SysOperationLog::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysOperationLogPageView::class))
            }
            .fetchSpringPage(pageable)

    fun findList(specification: SysOperationLogListSpecification): List<SysOperationLogPageView> =
        sql.createQuery(SysOperationLog::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysOperationLogPageView::class))
            }
            .execute()
}
