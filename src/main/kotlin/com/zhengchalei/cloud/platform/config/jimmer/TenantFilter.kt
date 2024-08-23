package com.zhengchalei.cloud.platform.config.jimmer

import com.zhengchalei.cloud.platform.config.security.SecurityUtils
import org.babyfish.jimmer.sql.event.EntityEvent
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.babyfish.jimmer.sql.kt.event.isChanged
import org.babyfish.jimmer.sql.kt.filter.KCacheableFilter
import org.babyfish.jimmer.sql.kt.filter.KFilterArgs
import org.springframework.stereotype.Component
import java.util.*

@Component
class TenantFilter : KCacheableFilter<TenantAware> {

    override fun filter(args: KFilterArgs<TenantAware>) {
        if (SecurityUtils.isLogin()) {
            args.apply {
                where(table.tenant.eq(SecurityUtils.getCurrentTenant()))
            }
        }
    }

    override fun getParameters(): SortedMap<String, Any>? {
        return sortedMapOf("tenant" to SecurityUtils.getCurrentTenant())
    }


    override fun isAffectedBy(e: EntityEvent<*>): Boolean {
        return e.isChanged(TenantAware::tenant)
    }
}