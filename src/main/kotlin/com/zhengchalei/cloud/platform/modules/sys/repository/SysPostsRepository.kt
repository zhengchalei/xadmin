/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.repository

import com.zhengchalei.cloud.platform.modules.sys.domain.SysPosts
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysPostsDetailView
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysPostsListSpecification
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysPostsPageView
import com.zhengchalei.cloud.platform.modules.sys.domain.id
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SysPostsRepository : KRepository<SysPosts, Long> {
    fun findDetailById(id: Long) =
        sql.createQuery(SysPosts::class) {
                where(table.id eq id)
                select(table.fetch(SysPostsDetailView::class))
            }
            .fetchOne()

    fun findPage(
        specification: SysPostsListSpecification,
        pageable: Pageable,
    ): Page<SysPostsPageView> =
        sql.createQuery(SysPosts::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysPostsPageView::class))
            }
            .fetchSpringPage(pageable)

    fun findList(specification: SysPostsListSpecification): List<SysPostsPageView> =
        sql.createQuery(SysPosts::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysPostsPageView::class))
            }
            .execute()
}
