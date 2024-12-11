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
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_role")
interface SysRole : BaseEntity {

    val name: String

    val code: String

    val description: String?

    @ManyToMany
    @JoinTable(name = "sys_role_permission", joinColumnName = "role_id", inverseJoinColumnName = "permission_id")
    val permissions: List<SysPermission>

    @ManyToMany(mappedBy = "roles") val users: List<SysUser>

    @IdView("permissions") val permissionIds: List<Long>

    @IdView("users") val userIds: List<Long>
}
