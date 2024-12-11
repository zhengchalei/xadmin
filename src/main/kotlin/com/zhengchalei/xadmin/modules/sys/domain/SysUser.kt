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

import com.fasterxml.jackson.annotation.JsonIgnore
import com.zhengchalei.xadmin.config.jimmer.entity.BaseEntity
import java.time.LocalDate
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_user")
interface SysUser : BaseEntity {

    @Key val username: String

    @get:JsonIgnore val password: String

    @Key val email: String

    val avatar: String?

    val status: Boolean

    val phoneNumber: String?

    val gender: Gender?

    val birthday: LocalDate?

    @ManyToOne val posts: SysPosts?

    @ManyToOne val department: SysDepartment?

    @ManyToMany
    @JoinTable(name = "sys_user_role", joinColumnName = "user_id", inverseJoinColumnName = "role_id")
    val roles: List<SysRole>

    @IdView("roles") val roleIds: List<Long>

    @IdView("department") val departmentId: Long?
}

@EnumType(EnumType.Strategy.NAME)
enum class Gender {
    @EnumItem(name = "MALE") MALE,
    @EnumItem(name = "FEMALE") FEMALE,
}
