/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.sys.service

import com.zhengchalei.xadmin.modules.sys.domain.SysDictItem
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysDictItemCreateInput
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysDictItemDetailView
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysDictItemListSpecification
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysDictItemUpdateInput
import com.zhengchalei.xadmin.modules.sys.repository.SysDictItemRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 系统字典服务
 *
 * @param [sysDictItemRepository] 系统词典存储库
 * @constructor 创建[SysDictItemService]
 * @author 郑查磊
 * @date 2024-08-17
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class SysDictItemService(private val sysDictItemRepository: SysDictItemRepository) {
    private val logger = org.slf4j.LoggerFactory.getLogger(SysDictItemService::class.java)

    /**
     * 查找系统字典通过ID
     *
     * @param [id] ID
     * @return [SysDictItemDetailView]
     */
    fun findSysDictItemById(id: Long): SysDictItemDetailView = this.sysDictItemRepository.findDetailById(id)

    /**
     * 查找系统字典列表
     *
     * @param [specification] 查询条件构造器
     */
    fun findSysDictItemList(specification: SysDictItemListSpecification) =
        this.sysDictItemRepository.findList(specification)

    /**
     * 查找系统字典分页
     *
     * @param [specification] 查询条件构造器
     * @param [pageable] 可分页
     */
    fun findSysDictItemPage(specification: SysDictItemListSpecification, pageable: Pageable) =
        this.sysDictItemRepository.findPage(specification, pageable)

    /**
     * 创建系统字典
     *
     * @param [sysDictItemCreateInput] 系统字典创建输入
     * @return [SysDictItemDetailView]
     */
    fun createSysDictItem(sysDictItemCreateInput: SysDictItemCreateInput): SysDictItemDetailView {
        val sysDictItem: SysDictItem = this.sysDictItemRepository.insert(sysDictItemCreateInput)
        return findSysDictItemById(sysDictItem.id)
    }

    /**
     * 更新系统字典通过ID
     *
     * @param [sysDictItemUpdateInput] 系统字典更新输入
     * @return [SysDictItemDetailView]
     */
    fun updateSysDictItemById(sysDictItemUpdateInput: SysDictItemUpdateInput): SysDictItemDetailView {
        val sysDictItem = this.sysDictItemRepository.update(sysDictItemUpdateInput)
        return findSysDictItemById(sysDictItem.id)
    }

    /**
     * 删除系统字典通过ID
     *
     * @param [id] ID
     */
    fun deleteSysDictItemById(id: Long) = this.sysDictItemRepository.deleteById(id)
}
