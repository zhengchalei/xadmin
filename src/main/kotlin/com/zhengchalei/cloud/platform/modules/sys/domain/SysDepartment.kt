package com.zhengchalei.cloud.platform.modules.sys.domain

import com.zhengchalei.cloud.platform.config.jimmer.TenantAware
import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "sys_department")
interface SysDepartment : TenantAware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    val name: String

    val sort: Int

    val status: Boolean

    val description: String?

    @ManyToOne
    val parent: SysDepartment?

    @OneToMany(mappedBy = "parent")
    val children: List<SysDepartment>
}