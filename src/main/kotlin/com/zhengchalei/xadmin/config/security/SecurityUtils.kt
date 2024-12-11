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
package com.zhengchalei.xadmin.config.security

import com.zhengchalei.xadmin.config.exceptions.NotLoginException
import com.zhengchalei.xadmin.config.exceptions.ServiceException
import com.zhengchalei.xadmin.config.jimmer.filter.DataScope
import com.zhengchalei.xadmin.config.security.authentication.SysUserAuthentication
import com.zhengchalei.xadmin.config.security.authentication.SysUserDetails
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

object SecurityUtils {
    fun getCurrentUsername(): String {
        val authentication: Authentication =
            SecurityContextHolder.getContext().authentication ?: throw NotLoginException()

        when (authentication.principal) {
            is SysUserDetails -> {
                val userDetails = authentication.principal as SysUserDetails
                return userDetails.username
            }

            is String -> {
                return authentication.principal as String
            }

            else -> {
                throw NotLoginException()
            }
        }
    }

    fun getCurrentUserId(): Long {
        val authentication: Authentication =
            SecurityContextHolder.getContext().authentication ?: throw NotLoginException()

        when (authentication) {
            is SysUserAuthentication -> {
                return authentication.id
            }

            else -> {
                throw NotLoginException()
            }
        }
    }

    fun getCurrentUserIdOrNull(): Long? {
        val authentication: Authentication =
            SecurityContextHolder.getContext().authentication ?: throw NotLoginException()
        return when (authentication) {
            is SysUserAuthentication -> {
                authentication.id
            }

            else -> {
                null
            }
        }
    }

    fun getCurrentUserDepartmentId(): Long {
        val authentication: Authentication =
            SecurityContextHolder.getContext().authentication ?: throw NotLoginException()
        return when (authentication) {
            is SysUserAuthentication -> {
                authentication.departmentId ?: throw ServiceException("未绑定部门信息")
            }
            else -> {
                throw NotLoginException()
            }
        }
    }

    fun getCurrentUserDepartmentIdOrNull(): Long? {
        val authentication: Authentication =
            SecurityContextHolder.getContext().authentication ?: throw NotLoginException()
        return when (authentication) {
            is SysUserAuthentication -> {
                authentication.departmentId
            }

            else -> {
                null
            }
        }
    }

    private fun getCaptchaAuthenticationToken(): SysUserAuthentication {
        if ((SecurityContextHolder.getContext().authentication is SysUserAuthentication).not()) {
            throw NotLoginException()
        }
        return SecurityContextHolder.getContext().authentication as SysUserAuthentication
    }

    fun getCurrentUserDataScope(): DataScope {
        val authentication = getCaptchaAuthenticationToken()
        return authentication.dataScope ?: DataScope.ALL
    }

    // 判断是否登录
    fun isLogin(): Boolean = SecurityContextHolder.getContext().authentication is SysUserAuthentication
}
