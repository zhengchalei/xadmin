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
package com.zhengchalei.xadmin.modules.generator.domain

import com.zhengchalei.xadmin.config.jimmer.entity.BaseEntity
import com.zhengchalei.xadmin.modules.sys.domain.SysPermission
import org.babyfish.jimmer.sql.*
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "gen_table")
interface Table : BaseEntity {

    /** 名称 */
    val name: String

    /** 包名 */
    val packageName: String

    /** 模块 */
    val module: String

    /** 表前缀 */
    val tablePrefix: String?

    /** 权限id */
    @Key val tableName: String

    /** 权限 */
    @ManyToOne @JoinColumn(name = "permission_id") val permission: SysPermission?

    @IdView("permission") val permissionId: Long?

    /** 创建时间 */
    val description: String?

    @OneToMany(mappedBy = "table") val columns: List<TableColumn>

    @IdView(value = "columns") val columnIds: List<Long>
}
