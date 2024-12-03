/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.sys.service

import com.zhengchalei.xadmin.modules.sys.domain.SysDict
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysDictCreateInput
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysDictDetailView
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysDictListSpecification
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysDictUpdateInput
import com.zhengchalei.xadmin.modules.sys.repository.SysDictRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 系统字典服务
 *
 * @param [sysDictRepository] 系统字典存储库
 * @constructor 创建[SysDictService]
 * @author 郑查磊
 * @date 2024-08-17
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class SysDictService(private val sysDictRepository: SysDictRepository) {
    private val logger = org.slf4j.LoggerFactory.getLogger(SysDictService::class.java)

    /**
     * 查找系统字典通过ID
     *
     * @param [id] ID
     * @return [SysDictDetailView]
     */
    fun findSysDictById(id: Long): SysDictDetailView = this.sysDictRepository.findDetailById(id)

    /**
     * 查找系统字典列表
     *
     * @param [specification] 查询条件构造器
     */
    fun findSysDictList(specification: SysDictListSpecification) = this.sysDictRepository.findList(specification)

    /**
     * 查找系统字典分页
     *
     * @param [specification] 查询条件构造器
     * @param [pageable] 可分页
     */
    fun findSysDictPage(specification: SysDictListSpecification, pageable: Pageable) =
        this.sysDictRepository.findPage(specification, pageable)

    /**
     * 创建系统字典
     *
     * @param [sysDictCreateInput] 系统字典创建输入
     * @return [SysDictDetailView]
     */
    fun createSysDict(sysDictCreateInput: SysDictCreateInput): SysDictDetailView {
        val sysDict: SysDict = this.sysDictRepository.insert(sysDictCreateInput)
        return findSysDictById(sysDict.id)
    }

    /**
     * 更新系统字典通过ID
     *
     * @param [sysDictUpdateInput] 系统字典更新输入
     * @return [SysDictDetailView]
     */
    fun updateSysDictById(sysDictUpdateInput: SysDictUpdateInput): SysDictDetailView {
        val sysDict = this.sysDictRepository.update(sysDictUpdateInput)
        return findSysDictById(sysDict.id)
    }

    /**
     * 删除系统字典通过ID
     *
     * @param [id] ID
     */
    fun deleteSysDictById(id: Long) = this.sysDictRepository.deleteById(id)
}
