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

import com.zhengchalei.xadmin.modules.sys.domain.dto.SysOperationLogDetailView
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysOperationLogListSpecification
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysOperationLogPageView
import com.zhengchalei.xadmin.modules.sys.repository.SysOperationLogRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 系统操作日志服务
 *
 * @constructor 创建[SysOperationLogService]
 * @author 郑查磊
 * @date 2024-08-17
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class SysOperationLogService(val sysOperationLogRepository: SysOperationLogRepository) {
    /**
     * 查找系统操作日志通过ID
     *
     * @param [id] ID
     * @return [SysOperationLogPageView]
     */
    fun findSysOperationLogById(id: Long): SysOperationLogDetailView = this.sysOperationLogRepository.findDetailById(id)

    /**
     * 查找系统操作日志列表
     *
     * @param [specification] 查询条件构造器
     * @return [List<SysOperationLogPageView>?]
     */
    fun findSysOperationLogList(specification: SysOperationLogListSpecification): List<SysOperationLogPageView>? =
        this.sysOperationLogRepository.findList(specification)

    /**
     * 查找系统操作日志分页
     *
     * @param [specification] 查询条件构造器
     * @param [page] 分页
     * @return [Page<SysOperationLogPageView>]
     */
    fun findSysOperationLogPage(
        specification: SysOperationLogListSpecification,
        page: PageRequest,
    ): Page<SysOperationLogPageView> = this.sysOperationLogRepository.findPage(specification, page)

    /**
     * 删除系统操作日志通过ID
     *
     * @param [id] ID
     */
    fun deleteSysOperationLogById(id: Long) {
        this.sysOperationLogRepository.deleteById(id)
    }
}
