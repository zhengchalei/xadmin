package com.zhengchalei.cloud.platform.modules.sys.service

import com.zhengchalei.cloud.platform.modules.sys.domain.SysDictItem
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysDictItemCreateInput
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysDictItemDetailView
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysDictItemPageSpecification
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysDictItemUpdateInput
import com.zhengchalei.cloud.platform.modules.sys.repository.SysDictItemRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 系统字典物品服务
 *
 * @param [sysDictItemRepository] 系统词典物品存储库
 * @constructor 创建[SysDictItemService]
 * @author 郑查磊
 * @date 2024-08-17
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class SysDictItemService(private val sysDictItemRepository: SysDictItemRepository) {
    private val logger = org.slf4j.LoggerFactory.getLogger(SysDictItemService::class.java)

    /**
     * 查找系统字典物品通过ID
     *
     * @param [id] ID
     * @return [SysDictItemDetailView]
     */
    fun findSysDictItemById(id: Long): SysDictItemDetailView =
        this.sysDictItemRepository.findDetailById(id)

    /**
     * 查找系统字典物品列表
     *
     * @param [specification] 查询条件构造器
     */
    fun findSysDictItemList(specification: SysDictItemPageSpecification) =
        this.sysDictItemRepository.findList(specification)

    /**
     * 查找系统字典物品分页
     *
     * @param [specification] 查询条件构造器
     * @param [pageable] 可分页
     */
    fun findSysDictItemPage(specification: SysDictItemPageSpecification, pageable: Pageable) =
        this.sysDictItemRepository.findPage(specification, pageable)

    /**
     * 创建系统字典物品
     *
     * @param [sysDictItemCreateInput] 系统字典物品创建输入
     * @return [SysDictItemDetailView]
     */
    fun createSysDictItem(sysDictItemCreateInput: SysDictItemCreateInput): SysDictItemDetailView {
        val sysDictItem: SysDictItem = this.sysDictItemRepository.insert(sysDictItemCreateInput)
        return findSysDictItemById(sysDictItem.id)
    }

    /**
     * 更新系统字典物品通过ID
     *
     * @param [sysDictItemUpdateInput] 系统字典物品更新输入
     * @return [SysDictItemDetailView]
     */
    fun updateSysDictItemById(
        sysDictItemUpdateInput: SysDictItemUpdateInput
    ): SysDictItemDetailView {
        val sysDictItem = this.sysDictItemRepository.update(sysDictItemUpdateInput)
        return findSysDictItemById(sysDictItem.id)
    }

    /**
     * 删除系统字典物品通过ID
     *
     * @param [id] ID
     */
    fun deleteSysDictItemById(id: Long) = this.sysDictItemRepository.deleteById(id)
}
