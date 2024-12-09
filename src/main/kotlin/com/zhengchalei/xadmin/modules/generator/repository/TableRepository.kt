/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.generator.repository

import com.zhengchalei.xadmin.modules.generator.domain.Table
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableDetailView
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableListSpecification
import com.zhengchalei.xadmin.modules.generator.domain.dto.TablePageView
import com.zhengchalei.xadmin.modules.generator.domain.id
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TableRepository : KRepository<Table, Long> {
    fun findDetailById(id: Long) =
        sql.createQuery(Table::class) {
                where(table.id eq id)
                select(table.fetch(TableDetailView::class))
            }
            .fetchOne()

    fun findPage(specification: TableListSpecification, pageable: Pageable): Page<TablePageView> =
        sql.createQuery(Table::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(TablePageView::class))
            }
            .fetchSpringPage(pageable)

    fun findList(specification: TableListSpecification): List<TablePageView> =
        sql.createQuery(Table::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(TablePageView::class))
            }
            .execute()
}
