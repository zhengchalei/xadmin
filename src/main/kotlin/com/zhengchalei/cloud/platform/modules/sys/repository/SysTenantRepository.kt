package com.zhengchalei.cloud.platform.modules.sys.repository

import com.zhengchalei.cloud.platform.modules.sys.domain.SysTenant
import com.zhengchalei.cloud.platform.modules.sys.domain.by
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysTenantDetailView
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysTenantPageSpecification
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysTenantPageView
import com.zhengchalei.cloud.platform.modules.sys.domain.id
import org.babyfish.jimmer.kt.new
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SysTenantRepository : KRepository<SysTenant, Long> {
    /**
     * 查找详情通过ID
     *
     * @param [id] ID
     */
    fun findDetailById(id: Long) =
        sql.createQuery(SysTenant::class) {
                where(table.id eq id)
                select(table.fetch(SysTenantDetailView::class))
            }
            .fetchOne()

    /**
     * 查找分页
     *
     * @param [specification] 查询条件构造器
     * @param [pageable] 可分页
     * @return [Page<SysTenantPageView>]
     */
    fun findPage(
        specification: SysTenantPageSpecification,
        pageable: Pageable,
    ): Page<SysTenantPageView> =
        sql.createQuery(SysTenant::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysTenantPageView::class))
            }
            .fetchSpringPage(pageable)

    /**
     * 查找列表
     *
     * @param [specification] 查询条件构造器
     * @return [List<SysTenantPageView>]
     */
    fun findList(specification: SysTenantPageSpecification): List<SysTenantPageView> =
        sql.createQuery(SysTenant::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(SysTenantPageView::class))
            }
            .execute()

    /**
     * 禁用系统租户通过ID
     *
     * @param [id] ID
     */
    fun disableSysTenantById(id: Long) {
        val sysTenant =
            new(SysTenant::class).by {
                this.id = id
                this.status = false
            }
        sql.update(sysTenant)
    }

    /**
     * 启用系统租户通过ID
     *
     * @param [id] ID
     */
    fun enableSysTenantById(id: Long) {
        val sysTenant =
            new(SysTenant::class).by {
                this.id = id
                this.status = true
            }
        sql.update(sysTenant)
    }
}
