package com.zhengchalei.cloud.platform.modules.sys.repository

import com.zhengchalei.cloud.platform.modules.sys.domain.SysDictItem
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysDictItemDetailView
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysDictItemPageSpecification
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysDictItemPageView
import com.zhengchalei.cloud.platform.modules.sys.domain.id
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SysDictItemRepository : KRepository<SysDictItem, Long> {
    fun findDetailById(id: Long) =
        sql
            .createQuery(SysDictItem::class) {
                orderBy(table.id.asc())
                where(table.id eq id)
                select(
                    table.fetch(SysDictItemDetailView::class),
                )
            }.fetchOne()

    fun findPage(
        specification: SysDictItemPageSpecification,
        pageable: Pageable,
    ): Page<SysDictItemPageView> =
        sql
            .createQuery(SysDictItem::class) {
                orderBy(table.id.asc())
                where(specification)
                select(
                    table.fetch(SysDictItemPageView::class),
                )
            }.fetchSpringPage(pageable)

    fun findList(specification: SysDictItemPageSpecification): List<SysDictItemPageView> =
        sql
            .createQuery(SysDictItem::class) {
                orderBy(table.id.asc())
                where(specification)
                select(
                    table.fetch(SysDictItemPageView::class),
                )
            }.execute()
}
