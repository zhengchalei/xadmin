/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.config.jimmer

import com.zhengchalei.xadmin.modules.sys.domain.SysDepartment
import com.zhengchalei.xadmin.modules.sys.domain.SysUser
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.MappedSuperclass

@MappedSuperclass
interface DataScopeAware {

    @ManyToOne @JoinColumn(name = "create_user_id") val createUser: SysUser

    @IdView("createUser") val createUserId: Long

    // 数据关联部门
    @ManyToOne @JoinColumn(name = "department_id") val department: SysDepartment

    @IdView("department") val departmentId: Long
}
