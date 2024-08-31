package com.zhengchalei.cloud.platform.modules.sys.service

import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.config.ServiceException
import com.zhengchalei.cloud.platform.modules.sys.domain.SysTenant
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysTenantCreateInput
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysTenantDetailView
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysTenantPageSpecification
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysTenantUpdateInput
import com.zhengchalei.cloud.platform.modules.sys.repository.SysTenantRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 系统租户服务
 * @author 郑查磊
 * @date 2024-08-17
 * @constructor 创建[SysTenantService]
 * @param [sysTenantRepository] 系统租户存储库
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class SysTenantService(
    private val sysTenantRepository: SysTenantRepository,
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(SysTenantService::class.java)

    /**
     * 查找系统租户通过ID
     * @param [id] ID
     * @return [SysTenantDetailView]
     */
    fun findSysTenantById(id: Long) = this.sysTenantRepository.findDetailById(id)

    /**
     * 查找系统租户列表
     * @param [specification] 查询条件构造器
     */
    fun findSysTenantList(specification: SysTenantPageSpecification) = this.sysTenantRepository.findList(specification)

    /**
     * 查找系统租户分页
     * @param [specification] 查询条件构造器
     * @param [pageable] 可分页
     */
    fun findSysTenantPage(
        specification: SysTenantPageSpecification,
        pageable: Pageable,
    ) = this.sysTenantRepository.findPage(specification, pageable)

    /**
     * 创造系统租户
     * @param [sysTenantCreateInput] 系统租户创建输入
     * @return [SysTenantDetailView]
     */
    fun createSysTenant(sysTenantCreateInput: SysTenantCreateInput): SysTenantDetailView {
        if (sysTenantCreateInput.code == Const.DefaultTenant) {
            throw ServiceException("租户不能为 ${Const.DefaultTenant}")
        }
        val sysTenant: SysTenant = this.sysTenantRepository.insert(sysTenantCreateInput)
        return findSysTenantById(sysTenant.id)
    }

    /**
     * 更新系统租户通过ID
     * @param [sysTenantUpdateInput] 系统租户更新输入
     * @return [SysTenantDetailView]
     */
    fun updateSysTenantById(sysTenantUpdateInput: SysTenantUpdateInput): SysTenantDetailView {
        val oldTenant = findSysTenantById(sysTenantUpdateInput.id)
        if (oldTenant.code != sysTenantUpdateInput.code) {
            throw ServiceException("租户 编码 不能修改")
        }
        val sysTenant = this.sysTenantRepository.update(sysTenantUpdateInput)
        return findSysTenantById(sysTenant.id)
    }

    /**
     * 删除系统租户通过ID
     * @param [id] ID
     */
    fun deleteSysTenantById(id: Long) {
        throw ServiceException("租户不应该被删除， 只能被禁用")
    }

    /**
     * 禁用系统租户通过ID
     * @param [id] ID
     */
    fun disableSysTenantById(id: Long) {
        this.sysTenantRepository.disableSysTenantById(id)
    }

    /**
     * 启用系统租户通过ID
     * @param [id] ID
     */
    fun enableSysTenantById(id: Long) {
        this.sysTenantRepository.enableSysTenantById(id)
    }
}
