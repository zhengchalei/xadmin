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
package com.zhengchalei.xadmin.modules.sys.service

import com.zhengchalei.xadmin.modules.sys.domain.dto.SysLoginLogDetailView
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysLoginLogListSpecification
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysLoginLogPageView
import com.zhengchalei.xadmin.modules.sys.repository.SysLoginLogRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 系统登录日志服务
 *
 * @constructor 创建[SysLoginLogService]
 * @author 郑查磊
 * @date 2024-08-17
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class SysLoginLogService(val sysLoginLogRepository: SysLoginLogRepository) {
    /**
     * 查找系统登录日志通过ID
     *
     * @param [id] ID
     * @return [SysLoginLogPageView]
     */
    fun findSysLoginLogById(id: Long): SysLoginLogDetailView = this.sysLoginLogRepository.findDetailById(id)

    /**
     * 查找系统登录日志列表
     *
     * @param [specification] 查询条件构造器
     * @return [List<SysLoginLogPageView>?]
     */
    fun findSysLoginLogList(specification: SysLoginLogListSpecification): List<SysLoginLogPageView>? =
        this.sysLoginLogRepository.findList(specification)

    /**
     * 查找系统登录日志分页
     *
     * @param [specification] 查询条件构造器
     * @param [page] 分页
     * @return [Page<SysLoginLogPageView>]
     */
    fun findSysLoginLogPage(specification: SysLoginLogListSpecification, page: PageRequest): Page<SysLoginLogPageView> =
        this.sysLoginLogRepository.findPage(specification, page)

    /**
     * 删除系统登录日志通过ID
     *
     * @param [id] ID
     */
    fun deleteSysLoginLogById(id: Long) {
        this.sysLoginLogRepository.deleteById(id)
    }
}
