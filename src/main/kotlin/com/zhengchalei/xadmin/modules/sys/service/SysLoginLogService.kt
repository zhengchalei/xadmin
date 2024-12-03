/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
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
