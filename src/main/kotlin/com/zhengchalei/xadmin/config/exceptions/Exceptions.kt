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
package com.zhengchalei.xadmin.config.exceptions

open class ServiceException(message: String = "服务异常") : RuntimeException(message)

class NotLoginException(message: String = "未登录") : ServiceException(message)

class LoginFailException(message: String = "登录失败, 请联系管理员") : ServiceException(message)

class UserNotFoundException(message: String = "用户不存在") : ServiceException(message)

class UserDisabledException(message: String = "用户被禁用") : ServiceException(message)

class UserPasswordErrorException(message: String = "密码错误") : ServiceException(message)

class UserPasswordExpiredException(message: String = "密码过期") : ServiceException(message)

class CaptchaErrorException(message: String = "验证码错误") : ServiceException(message)

class InvalidTokenException(message: String = "无效的令牌") : ServiceException(message)

class HasChildrenException(message: String = "当前节点存在子权限，无法删除") : ServiceException(message)

class EmptyFileException(message: String = "空文件不能上传") : ServiceException(message)

class TokenExpiredException(message: String = "令牌过期") : ServiceException(message)

class TokenInvalidException(message: String = "令牌无效") : ServiceException(message)
