/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.repository

import com.zhengchalei.cloud.platform.modules.sys.domain.SysDict
import com.zhengchalei.cloud.platform.modules.sys.domain.code
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysDictDetailView
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysDictPageSpecification
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysDictPageView
import com.zhengchalei.cloud.platform.modules.sys.domain.id
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SysDictRepository : KRepository<SysDict, Long> {
    fun findDetailById(id: Long) =
        sql.createQuery(SysDict::class) {
                where(table.id eq id)
                select(table.fetch(SysDictDetailView::class))
            }
            .fetchOne()

    fun findPage(specification: SysDictPageSpecification, pageable: Pageable): Page<SysDictPageView> =
        sql.createQuery(SysDict::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysDictPageView::class))
            }
            .fetchSpringPage(pageable)

    fun findList(specification: SysDictPageSpecification): List<SysDictPageView> =
        sql.createQuery(SysDict::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysDictPageView::class))
            }
            .execute()

    fun findByCode(code: String) =
        sql.createQuery(SysDict::class) {
                where(table.code eq code)
                select(table.fetch(SysDictDetailView::class))
            }
            .fetchOneOrNull()
}
