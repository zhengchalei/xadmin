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
package com.zhengchalei.xadmin.modules.generator.repository

import com.zhengchalei.xadmin.modules.generator.domain.Table
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableDetailView
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableListSpecification
import com.zhengchalei.xadmin.modules.generator.domain.dto.TablePageView
import com.zhengchalei.xadmin.modules.generator.domain.id
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TableRepository : KRepository<Table, Long> {
    fun findDetailById(id: Long) =
        sql.createQuery(Table::class) {
                where(table.id eq id)
                select(table.fetch(TableDetailView::class))
            }
            .fetchOne()

    fun findPage(specification: TableListSpecification, pageable: Pageable): Page<TablePageView> =
        sql.createQuery(Table::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(TablePageView::class))
            }
            .fetchSpringPage(pageable)

    fun findList(specification: TableListSpecification): List<TablePageView> =
        sql.createQuery(Table::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(TablePageView::class))
            }
            .execute()
}
