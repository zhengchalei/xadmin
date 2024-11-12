/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.repository

import com.zhengchalei.cloud.platform.config.security.SecurityUtils
import com.zhengchalei.cloud.platform.modules.sys.domain.*
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysUserDetailView
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysUserListSpecification
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysUserPageView
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

    fun findByUsername(username: String) =
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
}
