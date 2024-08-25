package com.zhengchalei.cloud.platform.modules.sys.domain

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_dict")
interface SysDict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    val name: String

    val code: String

    val sort: Int

    val description: String?

    val status: Boolean

    @OneToMany(mappedBy = "dict")
    val dictItems: List<SysDictItem>
}
