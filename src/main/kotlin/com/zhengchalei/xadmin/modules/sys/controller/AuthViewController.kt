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
package com.zhengchalei.xadmin.modules.sys.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AuthViewController {

    @GetMapping("/")
    fun index(): String {
        return "index"
    }

    @GetMapping(value = ["/login.html", "/login"])
    fun login(): String {
        return "login"
    }

    @GetMapping(value = ["/register", "/register.html"])
    fun register(): String {
        return "register"
    }

    @GetMapping(value = ["/rest-password", "/rest-password.html"])
    fun restPassword(): String {
        return "rest-password"
    }
}
