/*
Copyright 2024 [郑查磊]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
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
