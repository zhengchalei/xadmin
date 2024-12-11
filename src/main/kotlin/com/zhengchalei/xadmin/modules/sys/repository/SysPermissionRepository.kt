/*
Copyright 2024 [郑查磊]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.zhengchalei.xadmin.modules.sys.repository

import com.zhengchalei.xadmin.config.exceptions.HasChildrenException
import com.zhengchalei.xadmin.modules.sys.domain.SysPermission
import com.zhengchalei.xadmin.modules.sys.domain.dto.*
import com.zhengchalei.xadmin.modules.sys.domain.id
import com.zhengchalei.xadmin.modules.sys.domain.parentId
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
