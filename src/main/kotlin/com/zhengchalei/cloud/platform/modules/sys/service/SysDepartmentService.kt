package com.zhengchalei.cloud.platform.modules.sys.service

import com.zhengchalei.cloud.platform.modules.sys.domain.SysDepartment
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.*
import com.zhengchalei.cloud.platform.modules.sys.repository.SysDepartmentRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 系统部门服务
 * @author 郑查磊
 * @date 2024-08-17
 * @constructor 创建[SysDepartmentService]
 * @param [sysDepartmentRepository] 系统部门存储库
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class SysDepartmentService(
    private val sysDepartmentRepository: SysDepartmentRepository,
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(SysDepartmentService::class.java)

    /**
     * 查找系统部门通过ID
     * @param [id] ID
     * @return [SysDepartmentDetailView]
     */
    fun findSysDepartmentById(id: Long): SysDepartmentDetailView = this.sysDepartmentRepository.findDetailById(id)

    /**
     * 查找系统部门列表
     * @param [specification] 查询条件构造器
     */
    fun findSysDepartmentList(specification: SysDepartmentPageSpecification) = this.sysDepartmentRepository.findList(specification)

    /**
     * 查找系统部门树
     * @param [specification] 查询条件构造器
     */
    fun findSysDepartmentTree(specification: SysDepartmentPageSpecification) = this.sysDepartmentRepository.findTree(specification)

    /**
     * 查找系统部门分页
     * @param [specification] 查询条件构造器
     * @param [pageable] 可分页
     */
    fun findSysDepartmentPage(
        specification: SysDepartmentPageSpecification,
        pageable: Pageable,
    ) = this.sysDepartmentRepository.findPage(specification, pageable)

    /**
     * 创建系统部门
     * @param [sysDepartmentCreateInput] 系统部门创建输入
     * @return [SysDepartmentDetailView]
     */
    fun createSysDepartment(sysDepartmentCreateInput: SysDepartmentCreateInput): SysDepartmentDetailView {
        val sysDepartment: SysDepartment = this.sysDepartmentRepository.insert(sysDepartmentCreateInput)
        return findSysDepartmentById(sysDepartment.id)
    }

    /**
     * 更新系统部门通过ID
     * @param [sysDepartmentUpdateInput] 系统部门更新输入
     * @return [SysDepartmentDetailView]
     */
    fun updateSysDepartmentById(sysDepartmentUpdateInput: SysDepartmentUpdateInput): SysDepartmentDetailView {
        val sysDepartment = this.sysDepartmentRepository.update(sysDepartmentUpdateInput)
        return findSysDepartmentById(sysDepartment.id)
    }

    /**
     * 删除系统部门通过ID
     * @param [id] ID
     */
    fun deleteSysDepartmentById(id: Long) = this.sysDepartmentRepository.deleteById(id)

    /**
     * 查找系统部门树根
     * @param [specification] 查询条件构造器
     */
    fun findSysDepartmentTreeRoot(specification: SysDepartmentPageSpecification) = this.sysDepartmentRepository.findTreeRoot(specification)

    fun findSysDepartmentTreeSelect(specification: SysDepartmentPageSpecification): List<SysDepartmentTreeSelectView> =
        this.sysDepartmentRepository.findTreeSelect(specification)
}
