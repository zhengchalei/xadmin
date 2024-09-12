/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.config.jimmer

import com.zhengchalei.cloud.platform.config.security.SecurityUtils
import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.sql.DraftInterceptor
import org.springframework.stereotype.Component

@Component
class TenantDraftInterceptor : DraftInterceptor<TenantAware, TenantAwareDraft> {
    override fun beforeSave(draft: TenantAwareDraft, original: TenantAware?) {
        if (!isLoaded(draft, TenantAware::tenant)) {
            draft.tenant = SecurityUtils.getCurrentTenant()
        }
        if (original === null && !isLoaded(draft, TenantAware::tenant)) {
            draft.tenant = SecurityUtils.getCurrentTenant()
        }
    }
}
