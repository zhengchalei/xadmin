package com.zhengchalei.cloud.platform.modules.sys.repository

import com.zhengchalei.cloud.platform.modules.sys.domain.SysRole
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysRoleDetailView
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysRolePageSpecification
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysRolePageView
import com.zhengchalei.cloud.platform.modules.sys.domain.id
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SysRoleRepository : KRepository<SysRole, Long> {
    fun findDetailById(id: Long) =
        sql.createQuery(SysRole::class) {
                where(table.id eq id)
                select(table.fetch(SysRoleDetailView::class))
            }
            .fetchOne()

    fun findPage(
        specification: SysRolePageSpecification,
        pageable: Pageable,
    ): Page<SysRolePageView> =
        sql.createQuery(SysRole::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysRolePageView::class))
            }
            .fetchSpringPage(pageable)

    fun findList(specification: SysRolePageSpecification): List<SysRolePageView> =
        sql.createQuery(SysRole::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysRolePageView::class))
            }
            .execute()
}
