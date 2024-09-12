/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.repository

import com.zhengchalei.cloud.platform.config.HasChildrenException
import com.zhengchalei.cloud.platform.modules.sys.domain.SysDepartment
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.*
import com.zhengchalei.cloud.platform.modules.sys.domain.id
import com.zhengchalei.cloud.platform.modules.sys.domain.parentId
import com.zhengchalei.cloud.platform.modules.sys.domain.sort
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.isNull
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SysDepartmentRepository : KRepository<SysDepartment, Long> {
    fun findDetailById(id: Long) =
        sql.createQuery(SysDepartment::class) {
                where(table.id eq id)
                select(table.fetch(SysDepartmentDetailView::class))
            }
            .fetchOne()

    fun findPage(
        specification: SysDepartmentPageSpecification,
        pageable: Pageable,
    ): Page<SysDepartmentPageView> =
        sql.createQuery(SysDepartment::class) {
                orderBy(table.sort.asc())
                where(specification)
                select(table.fetch(SysDepartmentPageView::class))
            }
            .fetchSpringPage(pageable)

    fun findList(specification: SysDepartmentPageSpecification): List<SysDepartmentPageView> =
        sql.createQuery(SysDepartment::class) {
                orderBy(table.sort.asc())
                where(specification)
                select(table.fetch(SysDepartmentPageView::class))
            }
            .execute()

    fun findTree(specification: SysDepartmentPageSpecification) =
        sql.createQuery(SysDepartment::class) {
                orderBy(table.sort.asc())
                where(table.parentId.isNull())
                where(specification)
                select(table.fetch(SysDepartmentTreeView::class))
            }
            .execute()

    fun findTreeRoot(specification: SysDepartmentPageSpecification) =
        sql.createQuery(SysDepartment::class) {
                orderBy(table.sort.asc())
                where(table.parentId.isNull())
                where(specification)
                select(table.fetch(SysDepartmentTreeRootView::class))
            }
            .execute()

    fun findTreeSelect(
        specification: SysDepartmentPageSpecification
    ): List<SysDepartmentTreeSelectView> =
        sql.createQuery(SysDepartment::class) {
                orderBy(table.sort.asc())
                where(table.parentId.isNull())
                where(specification)
                select(table.fetch(SysDepartmentTreeSelectView::class))
            }
            .execute()

    fun deleteSysDepartmentById(id: Long) {
        val count =
            sql.createQuery(SysDepartment::class) {
                    where(table.parentId eq id)
                    select(count(table.id))
                }
                .fetchOne()
        if (count != 0L) {
            throw HasChildrenException()
        }
        return deleteById(id = id)
    }
}
