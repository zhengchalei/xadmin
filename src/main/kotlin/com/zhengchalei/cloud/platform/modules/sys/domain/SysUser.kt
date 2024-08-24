package com.zhengchalei.cloud.platform.modules.sys.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.zhengchalei.cloud.platform.config.jimmer.TenantAware
import org.babyfish.jimmer.sql.*
import java.time.LocalDate

@Entity
@Table(name = "sys_user")
interface SysUser : TenantAware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long

    @Key
    val username: String

    @get:JsonIgnore
    val password: String

    @Key
    val email: String

    val avatar: String?

    val status: Boolean

    val phoneNumber: String?

    val gender: Gender?

    val birthday: LocalDate?

    @ManyToOne
    val posts: SysPosts?

    @ManyToOne
    val department: SysDepartment?

    @ManyToMany
    @JoinTable(
        name = "sys_user_role",
        joinColumnName = "user_id",
        inverseJoinColumnName = "role_id"
    )
    val roles: List<SysRole>

    @IdView("roles")
    val roleIds: List<Long>
}

@EnumType(EnumType.Strategy.NAME)
enum class Gender {

    @EnumItem(name = "MALE")
    MALE,

    @EnumItem(name = "FEMALE")
    FEMALE
}
