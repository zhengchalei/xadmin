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
import org.babyfish.jimmer.sql.Entity
import org.babyfish.jimmer.sql.IdView
import org.babyfish.jimmer.sql.JoinColumn
import org.babyfish.jimmer.sql.Key
import org.babyfish.jimmer.sql.ManyToOne
import org.babyfish.jimmer.sql.Table

@Entity
@Table(name = "gen_table_column")
interface TableColumn : BaseEntity {

    @Key @ManyToOne @JoinColumn(name = "table_id") val table: com.zhengchalei.xadmin.modules.generator.domain.Table

    /** 表id */
    @IdView("table") val tableId: Long

    /** 字段名 */
    @Key val name: String

    /** 字段描述 */
    val comment: String?

    /** 字段类型 */
    val type: String?

    /** 字段长度 */
    val length: Int?

    /** 是否为空 */
    val nullable: Boolean

    /** 默认值 */
    val defaultValue: String?

    /** 唯一 */
    val unique: Boolean
}
