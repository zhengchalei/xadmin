/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.repository

import com.zhengchalei.cloud.platform.config.HasChildrenException
import com.zhengchalei.cloud.platform.modules.sys.domain.SysPermission
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.*
import com.zhengchalei.cloud.platform.modules.sys.domain.id
import com.zhengchalei.cloud.platform.modules.sys.domain.parentId
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.count
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.isNull
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SysPermissionRepository : KRepository<SysPermission, Long> {
    fun findDetailById(id: Long) =
        sql.createQuery(SysPermission::class) {
                where(table.id eq id)
                select(table.fetch(SysPermissionDetailView::class))
            }
            .fetchOne()

    fun findPage(specification: SysPermissionListSpecification, pageable: Pageable): Page<SysPermissionPageView> =
        sql.createQuery(SysPermission::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysPermissionPageView::class))
            }
            .fetchSpringPage(pageable)

    fun findList(specification: SysPermissionListSpecification): List<SysPermissionPageView> =
        sql.createQuery(SysPermission::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysPermissionPageView::class))
            }
            .execute()

    fun findTree(specification: SysPermissionListSpecification) =
        sql.createQuery(SysPermission::class) {
                orderBy(table.id.asc())
                where(table.parentId.isNull())
                where(specification)
                select(table.fetch(SysPermissionTreeView::class))
            }
            .execute()

    fun treeRoot(specification: SysPermissionListSpecification) =
        sql.createQuery(SysPermission::class) {
                orderBy(table.id.asc())
                where(table.parentId.isNull())
                where(specification)
                select(table.fetch(SysPermissionTreeRootView::class))
            }
            .execute()

    fun treeSelect(specification: SysPermissionListSpecification) =
        sql.createQuery(SysPermission::class) {
                orderBy(table.id.asc())
                where(specification)
                where(table.parentId.isNull())
                select(table.fetch(SysPermissionTreeSelectView::class))
            }
            .execute()

    fun deleteSysPermissionById(id: Long) {
        val count =
            sql.createQuery(SysPermission::class) {
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
