/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.service

import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysOperationLogDetailView
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysOperationLogListSpecification
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysOperationLogPageView
import com.zhengchalei.cloud.platform.modules.sys.repository.SysOperationLogRepository
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
