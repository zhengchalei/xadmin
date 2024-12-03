package com.zhengchalei.xadmin.config

import org.springframework.security.test.context.support.WithSecurityContext

@Retention(AnnotationRetention.RUNTIME)
@WithSecurityContext(factory = WithMockUserSecurityContextFactory::class)
annotation class WithMockUser(
    val id: Long = 1L,
    val username: String = "admin",
    val name: String = "超级管理员",
    val password: String = "admin",
    val roles: Array<String> = ["ADMIN"],
    val authorities: Array<String> = [],
)
