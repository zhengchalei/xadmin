package com.zhengchalei.xadmin.config.jimmer.interceptor

import com.zhengchalei.xadmin.config.jimmer.BaseEntity
import com.zhengchalei.xadmin.config.jimmer.BaseEntityDraft
import com.zhengchalei.xadmin.config.jimmer.DataScopeAware
import com.zhengchalei.xadmin.config.jimmer.DataScopeAwareDraft
import com.zhengchalei.xadmin.config.security.SecurityUtils
import com.zhengchalei.xadmin.modules.sys.domain.SysUser
import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.kt.makeIdOnly
import org.babyfish.jimmer.sql.DraftInterceptor
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DataScopeDraftInterceptor(
) : DraftInterceptor<DataScopeAware, DataScopeAwareDraft> {

    override fun beforeSave(draft: DataScopeAwareDraft, original: DataScopeAware?) {
        if (!isLoaded(draft, DataScopeAware::createUser)) {
            // 这里是修改
        }

        if (original === null) {
            if (!isLoaded(draft, DataScopeAware::createUser)) {
                SecurityUtils.getCurrentUserIdOrNull()?.let { id ->
                    draft.createUser = makeIdOnly(SysUser::class, id)
                }
            }
        }
    }
}