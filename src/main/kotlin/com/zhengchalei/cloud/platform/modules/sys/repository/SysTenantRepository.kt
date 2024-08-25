package com.zhengchalei.cloud.platform.modules.sys.repository

import com.zhengchalei.cloud.platform.modules.sys.domain.SysTenant
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysTenantPageSpecification
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysTenantPageView
import com.zhengchalei.cloud.platform.modules.sys.domain.id
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SysTenantRepository : KRepository<SysTenant, Long> {
    fun findDetailById(id: Long) =
        sql
            .createQuery(SysTenant::class) {
                where(table.id eq id)
                select(
                    table.fetch(SysTenantPageView::class),
                )
            }.fetchOne()

    fun findPage(
        specification: SysTenantPageSpecification,
        pageable: Pageable,
    ): Page<SysTenantPageView> =
        sql
            .createQuery(SysTenant::class) {
                orderBy(table.id.asc())
                where(specification)
                select(
                    table.fetch(SysTenantPageView::class),
                )
            }.fetchSpringPage(pageable)

    fun findList(specification: SysTenantPageSpecification): List<SysTenantPageView> =
        sql
            .createQuery(SysTenant::class) {
                orderBy(table.id.asc())
                where(specification)
                select(
                    table.fetch(SysTenantPageView::class),
                )
            }.execute()
}
