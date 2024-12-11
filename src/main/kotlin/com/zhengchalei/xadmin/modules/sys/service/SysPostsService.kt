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

import com.zhengchalei.xadmin.modules.sys.domain.SysPosts
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysPostsCreateInput
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysPostsDetailView
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysPostsListSpecification
import com.zhengchalei.xadmin.modules.sys.domain.dto.SysPostsUpdateInput
import com.zhengchalei.xadmin.modules.sys.repository.SysPostsRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 系统岗位服务
 *
 * @param [sysPostRepository] 系统post存储库
 * @constructor 创建[SysPostsService]
 * @author 郑查磊
 * @date 2024-08-17
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class SysPostsService(private val sysPostRepository: SysPostsRepository) {
    private val logger = org.slf4j.LoggerFactory.getLogger(SysPostsService::class.java)

    /**
     * 查找系统岗位通过ID
     *
     * @param [id] ID
     * @return [SysPostsDetailView]
     */
    fun findSysPostsById(id: Long): SysPostsDetailView = this.sysPostRepository.findDetailById(id)

    /**
     * 查找系统岗位列表
     *
     * @param [specification] 查询条件构造器
     */
    fun findSysPostsList(specification: SysPostsListSpecification) = this.sysPostRepository.findList(specification)

    /**
     * 查找系统岗位分页
     *
     * @param [specification] 查询条件构造器
     * @param [pageable] 可分页
     */
    fun findSysPostsPage(specification: SysPostsListSpecification, pageable: Pageable) =
        this.sysPostRepository.findPage(specification, pageable)

    /**
     * 创造系统岗位
     *
     * @param [sysPostCreateInput] 系统岗位输入
     * @return [SysPostsDetailView]
     */
    fun createSysPosts(sysPostCreateInput: SysPostsCreateInput): SysPostsDetailView {
        val sysPost: SysPosts = this.sysPostRepository.insert(sysPostCreateInput)
        return findSysPostsById(sysPost.id)
    }

    /**
     * 更新系统岗位通过ID
     *
     * @param [sysPostUpdateInput] 系统邮政更新输入
     * @return [SysPostsDetailView]
     */
    fun updateSysPostsById(sysPostUpdateInput: SysPostsUpdateInput): SysPostsDetailView {
        val sysPost = this.sysPostRepository.update(sysPostUpdateInput)
        return findSysPostsById(sysPost.id)
    }

    /**
     * 删除
     *
     * @param [id] ID
     */
    fun deleteSysPostsById(id: Long) = this.sysPostRepository.deleteById(id)
}
