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
package com.zhengchalei.xadmin.modules.sys.domain

import com.zhengchalei.xadmin.config.jimmer.entity.BaseEntity
import com.zhengchalei.xadmin.config.jimmer.filter.DataScope
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_department")
interface SysDepartment : BaseEntity {

    val name: String

    val sort: Int

    val status: Boolean

    val description: String?

    @ManyToOne val parent: SysDepartment?

    @OneToMany(mappedBy = "parent") val children: List<SysDepartment>

    val dataScope: DataScope

    @ManyToMany
    @JoinTable(
        name = "sys_department_data_scope",
        joinColumnName = "department_id",
        inverseJoinColumnName = "data_scope_department_id",
    )
    val dataScopeDepartments: List<SysDepartment>

    @IdView("dataScopeDepartments") val dataScopeDepartmentIds: List<Long>
}
