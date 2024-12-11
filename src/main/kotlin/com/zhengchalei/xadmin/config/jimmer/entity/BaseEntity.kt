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
package com.zhengchalei.xadmin.config.jimmer.entity

import com.zhengchalei.xadmin.modules.sys.domain.SysUser
import java.time.LocalDateTime
import org.babyfish.jimmer.sql.*

@MappedSuperclass
interface BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long

    val createTime: LocalDateTime

    val updateTime: LocalDateTime

    @ManyToOne @JoinColumn(name = "create_by") val createBy: SysUser?

    @IdView("createBy") val createById: Long?

    @ManyToOne @JoinColumn(name = "update_by") val updateBy: SysUser?

    @IdView("updateBy") val updateById: Long?
}
