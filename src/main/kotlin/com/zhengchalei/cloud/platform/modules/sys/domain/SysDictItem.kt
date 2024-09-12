package com.zhengchalei.cloud.platform.modules.sys.domain

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_dict_item")
interface SysDictItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long

    val name: String

    val code: String

    val data: String

    val sort: Int

    val description: String?

    val status: Boolean

    @OnDissociate(DissociateAction.DELETE)
    @ManyToOne
    @JoinColumn(name = "dict_id")
    val dict: SysDict
}
