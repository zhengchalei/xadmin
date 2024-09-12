/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.config.jimmer

import com.zhengchalei.cloud.platform.config.security.SecurityUtils
import java.util.*
import org.babyfish.jimmer.sql.event.EntityEvent
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.event.isChanged
import org.babyfish.jimmer.sql.kt.filter.KCacheableFilter
import org.babyfish.jimmer.sql.kt.filter.KFilterArgs
import org.springframework.stereotype.Component

@Component
class TenantFilter : KCacheableFilter<TenantAware> {
    override fun filter(args: KFilterArgs<TenantAware>) {
        if (SecurityUtils.isLogin()) {
            args.apply { where(table.tenant.eq(SecurityUtils.getCurrentTenant())) }
        }
    }

    override fun getParameters(): SortedMap<String, Any>? =
        sortedMapOf("tenant" to SecurityUtils.getCurrentTenant())

    override fun isAffectedBy(e: EntityEvent<*>): Boolean = e.isChanged(TenantAware::tenant)
}
