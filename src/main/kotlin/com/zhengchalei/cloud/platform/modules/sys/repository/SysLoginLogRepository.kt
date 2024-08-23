package com.zhengchalei.cloud.platform.modules.sys.repository

import com.zhengchalei.cloud.platform.modules.sys.domain.SysLoginLog
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysLoginLogDetailView
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysLoginLogPageSpecification
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysLoginLogPageView
import com.zhengchalei.cloud.platform.modules.sys.domain.id
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface SysLoginLogRepository : KRepository<SysLoginLog, Long> {

    fun findDetailById(id: Long) = sql.createQuery(SysLoginLog::class) {
        where(table.id eq id)
        select(
            table.fetch(SysLoginLogDetailView::class)
        )
    }.fetchOne()

    fun findPage(specification: SysLoginLogPageSpecification, pageable: Pageable): Page<SysLoginLogPageView> =
        sql.createQuery(SysLoginLog::class) {
            orderBy(table.id.asc())
            where(specification)
            select(
                table.fetch(SysLoginLogPageView::class)
            )
        }.fetchSpringPage(pageable)

    fun findList(specification: SysLoginLogPageSpecification): List<SysLoginLogPageView> =
        sql.createQuery(SysLoginLog::class) {
            orderBy(table.id.asc())
            where(specification)
            select(
                table.fetch(SysLoginLogPageView::class)
            )
        }.execute()


}