package com.zhengchalei.cloud.platform.config.jimmer

import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.modules.sys.domain.SysUser
import com.zhengchalei.cloud.platform.modules.sys.domain.username
import org.babyfish.jimmer.sql.kt.ast.expression.ne
import org.babyfish.jimmer.sql.kt.filter.KFilter
import org.babyfish.jimmer.sql.kt.filter.KFilterArgs
import org.springframework.stereotype.Component

/**
 * 超级管理员过滤器, 这个是一个超级管理员的角色, 不会被查询到
 *
 * @constructor 创建[SuperAdminFilter]
 * @author 郑查磊
 * @date 2024-08-29
 */
@Component
class SuperAdminFilter : KFilter<SysUser> {
    override fun filter(args: KFilterArgs<SysUser>) {
        args.apply { where(table.username.ne(Const.Root)) }
    }
}
