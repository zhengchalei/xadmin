package com.zhengchalei.cloud.platform.modules.sys.repository

import com.zhengchalei.cloud.platform.config.jimmer.TenantFilter
import com.zhengchalei.cloud.platform.config.security.SecurityUtils
import com.zhengchalei.cloud.platform.modules.sys.domain.*
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysUserDetailView
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysUserPageSpecification
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysUserPageView
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface SysUserRepository : KRepository<SysUser, Long> {

    fun findDetailById(id: Long) = sql.createQuery(SysUser::class) {
        where(table.id eq id)
        select(
            table.fetch(SysUserDetailView::class)
        )
    }.fetchOne()

    fun findPage(specification: SysUserPageSpecification, pageable: Pageable): Page<SysUserPageView> =
        sql.createQuery(SysUser::class) {
            where(specification)
            select(
                table.fetch(SysUserPageView::class)
            )
        }.fetchSpringPage(pageable)

    fun findList(specification: SysUserPageSpecification): List<SysUserPageView> = sql.createQuery(SysUser::class) {
        where(specification)
        select(
            table.fetch(SysUserPageView::class)
        )
    }.execute()

    fun currentUserInfo(): SysUser {
        val username = SecurityUtils.getCurrentUsername()
        return sql.createQuery(SysUser::class) {
            where(table.username eq username)
            select(
                table.fetchBy {
                    allScalarFields()
                    roles {
                        allScalarFields()
                        permissions {
                            allScalarFields()
                        }
                    }
                }
            )
        }.fetchOne()
    }

    fun findByUsernameAndTenant(username: String, tenant: String) = sql
        .filters {
            disableByTypes(TenantFilter::class)
        }
        .createQuery(SysUser::class) {
            where(table.username eq username)
            where(table.tenant eq tenant)
            select(
                table.fetchBy {
                    allScalarFields()
                    roles {
                        allScalarFields()
                        permissions {
                            allScalarFields()
                        }
                    }
                }
            )
        }.fetchOneOrNull()

}
