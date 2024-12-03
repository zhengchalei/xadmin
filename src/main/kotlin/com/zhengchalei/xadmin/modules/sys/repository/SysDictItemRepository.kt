/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.sys.repository

import com.zhengchalei.xadmin.modules.sys.domain.SysDictItem
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysDictItemDetailView
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysDictItemListSpecification
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysDictItemPageView
import com.zhengchalei.xadmin.modules.sys.domain.id
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SysDictItemRepository : KRepository<SysDictItem, Long> {
    fun findDetailById(id: Long) =
        sql.createQuery(SysDictItem::class) {
                orderBy(table.id.asc())
                where(table.id eq id)
                select(table.fetch(SysDictItemDetailView::class))
            }
            .fetchOne()

    fun findPage(specification: SysDictItemListSpecification, pageable: Pageable): Page<SysDictItemPageView> =
        sql.createQuery(SysDictItem::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysDictItemPageView::class))
            }
            .fetchSpringPage(pageable)

    fun findList(specification: SysDictItemListSpecification): List<SysDictItemPageView> =
        sql.createQuery(SysDictItem::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysDictItemPageView::class))
            }
            .execute()
}
