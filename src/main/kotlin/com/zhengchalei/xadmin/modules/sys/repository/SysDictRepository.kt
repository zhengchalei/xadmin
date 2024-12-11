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

import com.zhengchalei.xadmin.modules.sys.domain.SysDict
import com.zhengchalei.xadmin.modules.sys.domain.code
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysDictDetailView
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysDictListSpecification
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysDictPageView
import com.zhengchalei.xadmin.modules.sys.domain.id
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

    fun findPage(specification: SysDictListSpecification, pageable: Pageable): Page<SysDictPageView> =
        sql.createQuery(SysDict::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysDictPageView::class))
            }
            .fetchSpringPage(pageable)

    fun findList(specification: SysDictListSpecification): List<SysDictPageView> =
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
