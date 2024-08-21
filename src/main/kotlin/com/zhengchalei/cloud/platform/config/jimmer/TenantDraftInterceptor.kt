package com.zhengchalei.cloud.platform.config.jimmer

import com.zhengchalei.cloud.platform.config.security.SecurityUtils
import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.sql.DraftInterceptor
import org.springframework.stereotype.Component

@Component
class TenantDraftInterceptor(
) : DraftInterceptor<TenantAware, TenantAwareDraft> {

    override fun beforeSave(draft: TenantAwareDraft, original: TenantAware?) {
        if (!isLoaded(draft, TenantAware::tenant)) {
            draft.tenant = SecurityUtils.getCurrentTenant()
        }
        if (original === null) {
            if (!isLoaded(draft, TenantAware::tenant)) {
                draft.tenant = SecurityUtils.getCurrentTenant()
            }
        }
    }
}