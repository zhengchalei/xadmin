/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.cloud.platform.modules.sys.service

import com.zhengchalei.cloud.platform.modules.sys.domain.SysDepartment
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.*
import com.zhengchalei.cloud.platform.modules.sys.repository.SysDepartmentRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 系统部门服务
 *
 * @param [sysDepartmentRepository] 系统部门存储库
 * @constructor 创建[SysDepartmentService]
 * @author 郑查磊
 * @date 2024-08-17
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class SysDepartmentService(private val sysDepartmentRepository: SysDepartmentRepository) {
    private val log = org.slf4j.LoggerFactory.getLogger(SysDepartmentService::class.java)

    /**
     * 查找系统部门通过ID
     *
     * @param [id] ID
     * @return [SysDepartmentDetailView]
     */
    fun findSysDepartmentById(id: Long): SysDepartmentDetailView {
        log.info("查找系统部门通过ID: {}", id)
        val data = this.sysDepartmentRepository.findDetailById(id)
        log.debug("查找系统部门通过ID: {}, 查询结果: {}", id, data)
        return data
    }

    /**
     * 查找系统部门列表
     *
     * @param [specification] 查询条件构造器
     */
    fun findSysDepartmentList(specification: SysDepartmentListSpecification): List<SysDepartmentPageView> {
        log.info("查找系统部门列表, 查询条件: {}", specification)
        val data = this.sysDepartmentRepository.findList(specification)
        log.debug("查找系统部门列表, 查询条件: {}, 查询结果: {}", specification, data)
        return data
    }

    /**
     * 查找系统部门树
     *
     * @param [specification] 查询条件构造器
     */
    fun findSysDepartmentTree(specification: SysDepartmentListSpecification): List<SysDepartmentTreeView> {
        log.info("查找系统部门树, 查询条件: {}", specification)
        val data = this.sysDepartmentRepository.findTree(specification)
        log.debug("查找系统部门树, 查询条件: {}, 查询结果: {}", specification, data)
        return data
    }

    /**
     * 查找系统部门分页
     *
     * @param [specification] 查询条件构造器
     * @param [pageable] 可分页
     */
    fun findSysDepartmentPage(
        specification: SysDepartmentListSpecification,
        pageable: Pageable,
    ): Page<SysDepartmentPageView> {
        log.info("查找系统部门分页, 查询条件: {}, 分页参数: {}", specification, pageable)
        val data = this.sysDepartmentRepository.findPage(specification, pageable)
        log.debug("查找系统部门分页, 查询条件: {}, 查询结果: {}", specification, data)
        return data
    }

    /**
     * 创建系统部门
     *
     * @param [sysDepartmentCreateInput] 系统部门创建输入
     * @return [SysDepartmentDetailView]
     */
    fun createSysDepartment(sysDepartmentCreateInput: SysDepartmentCreateInput): SysDepartmentDetailView {
        log.info("创建系统部门, 输入参数: {}", sysDepartmentCreateInput)
        val sysDepartment: SysDepartment = this.sysDepartmentRepository.insert(sysDepartmentCreateInput)
        log.info("创建系统部门, 创建结果: {}", sysDepartment)
        val data = findSysDepartmentById(sysDepartment.id)
        return data
    }

    /**
     * 更新系统部门通过ID
     *
     * @param [sysDepartmentUpdateInput] 系统部门更新输入
     * @return [SysDepartmentDetailView]
     */
    fun updateSysDepartmentById(sysDepartmentUpdateInput: SysDepartmentUpdateInput): SysDepartmentDetailView {
        log.info("更新系统部门通过ID, 输入参数: {}", sysDepartmentUpdateInput)
        val sysDepartment = this.sysDepartmentRepository.update(sysDepartmentUpdateInput)
        log.info("更新系统部门通过ID, 更新结果: {}", sysDepartment)
        return findSysDepartmentById(sysDepartment.id)
    }

    /**
     * 删除系统部门通过ID
     *
     * @param [id] ID
     */
    fun deleteSysDepartmentById(id: Long) {
        log.info("删除系统部门通过ID: {}", id)
        this.sysDepartmentRepository.deleteSysDepartmentById(id)
        log.info("删除系统部门通过ID: {} 成功", id)
    }

    /**
     * 查找系统部门树根
     *
     * @param [specification] 查询条件构造器
     */
    fun findSysDepartmentTreeRoot(specification: SysDepartmentListSpecification): List<SysDepartmentTreeRootView> {
        log.info("查找系统部门树根, 查询条件: {}", specification)
        val data = this.sysDepartmentRepository.findTreeRoot(specification)
        log.debug("查找系统部门树根, 查询结果: {}", data)
        return data
    }

    /**
     * 查找系统部门树选择
     *
     * @param [specification] 查询条件构造器
     * @return [List<SysDepartmentTreeSelectView>]
     */
    fun findSysDepartmentTreeSelect(specification: SysDepartmentListSpecification): List<SysDepartmentTreeSelectView> {
        log.info("查找系统部门树选择, 查询条件: {}", specification)
        val data = this.sysDepartmentRepository.findTreeSelect(specification)
        log.debug("查找系统部门树选择, 查询结果: {}", data)
        return data
    }
}
