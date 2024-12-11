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

import com.zhengchalei.xadmin.modules.sys.domain.SysOperationLog
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysOperationLogDetailView
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysOperationLogListSpecification
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysOperationLogPageView
import com.zhengchalei.xadmin.modules.sys.domain.id
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SysOperationLogRepository : KRepository<SysOperationLog, Long> {
    fun findDetailById(id: Long) =
        sql.createQuery(SysOperationLog::class) {
                where(table.id eq id)
                select(table.fetch(SysOperationLogDetailView::class))
            }
            .fetchOne()

    fun findPage(specification: SysOperationLogListSpecification, pageable: Pageable): Page<SysOperationLogPageView> =
        sql.createQuery(SysOperationLog::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysOperationLogPageView::class))
            }
            .fetchSpringPage(pageable)

    fun findList(specification: SysOperationLogListSpecification): List<SysOperationLogPageView> =
        sql.createQuery(SysOperationLog::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysOperationLogPageView::class))
            }
            .execute()
}
