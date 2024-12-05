/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.sys.domain

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

    @OnDissociate(DissociateAction.DELETE) @ManyToOne @JoinColumn(name = "dict_id") val dict: SysDict
}