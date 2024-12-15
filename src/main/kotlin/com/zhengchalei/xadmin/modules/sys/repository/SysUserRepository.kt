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

import com.zhengchalei.xadmin.config.security.SecurityUtils
import com.zhengchalei.xadmin.modules.sys.domain.*
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysUserDetailView
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysUserExportView
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysUserListSpecification
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysUserPageView
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.ast.expression.valueIn
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SysUserRepository : KRepository<SysUser, Long> {
    fun findDetailById(id: Long) =
        sql.createQuery(SysUser::class) {
                where(table.id eq id)
                select(table.fetch(SysUserDetailView::class))
            }
            .fetchOne()

    fun findPage(specification: SysUserListSpecification, pageable: Pageable): Page<SysUserPageView> =
        sql.createQuery(SysUser::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysUserPageView::class))
            }
            .fetchSpringPage(pageable)

    fun findPage(
        specification: SysUserListSpecification,
        pageable: Pageable,
        sysDepartmentIds: List<Long>,
    ): Page<SysUserPageView> {
        return sql.createQuery(SysUser::class) {
                orderBy(table.id.asc())
                where(specification)
                where(table.departmentId valueIn sysDepartmentIds)
                select(table.fetch(SysUserPageView::class))
            }
            .fetchSpringPage(pageable)
    }

    fun findList(specification: SysUserListSpecification): List<SysUserPageView> =
        sql.createQuery(SysUser::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysUserPageView::class))
            }
            .execute()

    fun currentUserInfo(): SysUser {
        val username = SecurityUtils.getCurrentUsername()
        return sql.createQuery(SysUser::class) {
                where(table.username eq username)
                select(
                    table.fetchBy {
                        allScalarFields()
                        roles {
                            allScalarFields()
                            permissions { allScalarFields() }
                        }
                    }
                )
            }
            .fetchOne()
    }

    fun findUserDetailsByUsername(username: String) =
        sql.createQuery(SysUser::class) {
                where(table.username eq username)
                select(
                    table.fetchBy {
                        allScalarFields()
                        roles {
                            allScalarFields()
                            permissions { allScalarFields() }
                        }
                        department { allScalarFields() }
                    }
                )
            }
            .fetchOneOrNull()

    fun findByUsernameForLogin(username: String) =
        sql.createQuery(SysUser::class) {
                where(table.username eq username)
                select(
                    table.fetchBy {
                        allScalarFields()
                        roles {
                            allScalarFields()
                            permissions { allScalarFields() }
                        }
                    }
                )
            }
            .fetchOneOrNull()

    fun currentUserId(): Long {
        val username = SecurityUtils.getCurrentUsername()
        return sql.createQuery(SysUser::class) {
                where(table.username eq username)
                select(table.fetchBy { table.id })
            }
            .fetchOne()
            .id
    }

    fun findSysUserExportList(specification: SysUserListSpecification): List<SysUserExportView> {
        return sql.createQuery(SysUser::class) {
                where(specification)
                select(table.fetch(SysUserExportView::class))
            }
            .execute()
    }
}
