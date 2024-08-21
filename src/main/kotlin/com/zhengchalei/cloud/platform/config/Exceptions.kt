package com.zhengchalei.cloud.platform.config

open class ServiceException(message: String = "服务异常") : RuntimeException(message)

class NotLoginException(message: String = "未登录") : ServiceException(message)

class LoginFailException(message: String = "登录失败, 请联系管理员") : ServiceException(message)

class TenantNotFoundException(message: String = "租户不存在") : ServiceException(message)

class UserNotFoundException(message: String = "用户不存在") : ServiceException(message)

class UserDisabledException(message: String = "用户被禁用") : ServiceException(message)

class UserPasswordErrorException(message: String = "密码错误") : ServiceException(message)

class UserPasswordExpiredException(message: String = "密码过期") : ServiceException(message)

class CaptchaErrorException(message: String = "验证码错误") : ServiceException(message)

class SwitchTenantException(message: String = "切换租户失败") : ServiceException(message)

class InvalidTokenException(message: String = "无效的令牌") : ServiceException(message)