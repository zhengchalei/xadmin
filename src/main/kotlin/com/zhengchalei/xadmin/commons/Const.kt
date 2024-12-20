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
package com.zhengchalei.xadmin.commons

object Const {
    const val ADMIN_USER = "admin"

    const val ADMIN_ROLE = "admin"

    const val DEFAULT_PASSWORD = "123456"

    const val SECURITY_ROLE_PREFIX = "ROLE_"

    // env
    const val ENV_PROD = "prod"
    const val ENV_DEV = "dev"
    const val ENV_TEST = "test"

    // const token header
    const val TOKEN_HEADER = "x-token"

    // key prefix
    const val REGISTER_KEY = "register:code"
    const val REST_PASSWORD_KEY = "rest-password:code"
}
