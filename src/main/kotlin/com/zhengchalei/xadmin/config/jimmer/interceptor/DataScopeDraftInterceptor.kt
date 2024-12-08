/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.config.jimmer.interceptor

import com.zhengchalei.xadmin.config.jimmer.entity.DataScopeEntity
import com.zhengchalei.xadmin.config.jimmer.entity.DataScopeEntityDraft
import com.zhengchalei.xadmin.config.security.SecurityUtils
import com.zhengchalei.xadmin.modules.sys.domain.SysUser
import org.babyfish.jimmer.kt.isLoaded
import org.babyfish.jimmer.kt.makeIdOnly
import org.babyfish.jimmer.sql.DraftInterceptor
import org.springframework.stereotype.Component

@Component
class DataScopeDraftInterceptor() : DraftInterceptor<DataScopeEntity, DataScopeEntityDraft> {

    override fun beforeSave(draft: DataScopeEntityDraft, original: DataScopeEntity?) {
        if (!isLoaded(draft, DataScopeEntity::createBy)) {
            // 这里是修改
        }

        if (original === null) {
            if (!isLoaded(draft, DataScopeEntity::createBy)) {
                SecurityUtils.getCurrentUserIdOrNull()?.let { id -> draft.createBy = makeIdOnly(SysUser::class, id) }
            }
        }
    }
}
