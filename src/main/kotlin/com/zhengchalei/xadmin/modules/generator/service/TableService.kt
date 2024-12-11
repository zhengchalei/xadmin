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
package com.zhengchalei.xadmin.modules.generator.service

import com.zhengchalei.xadmin.modules.generator.domain.Table
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableCreateInput
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableDetailView
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableListSpecification
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableUpdateInput
import com.zhengchalei.xadmin.modules.generator.repository.TableRepository
import com.zhengchalei.xadmin.modules.generator.template.DefaultGeneratorTemplate
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 代码生成表服务
 *
 * @param [tableRepository] 代码生成表存储库
 * @constructor 创建[TableService]
 * @author 郑查磊
 * @date 2024-08-17
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class TableService(
    private val tableRepository: TableRepository,
    private val defaultGeneratorTemplate: DefaultGeneratorTemplate,
) {
    private val logger = org.slf4j.LoggerFactory.getLogger(TableService::class.java)

    /**
     * 查找代码生成表通过ID
     *
     * @param [id] ID
     * @return [TableDetailView]
     */
    fun findTableById(id: Long): TableDetailView = this.tableRepository.findDetailById(id)

    /**
     * 查找代码生成表列表
     *
     * @param [specification] 查询条件构造器
     */
    fun findTableList(specification: TableListSpecification) = this.tableRepository.findList(specification)

    /**
     * 查找代码生成表分页
     *
     * @param [specification] 查询条件构造器
     * @param [pageable] 可分页
     */
    fun findTablePage(specification: TableListSpecification, pageable: Pageable) =
        this.tableRepository.findPage(specification, pageable)

    /**
     * 创造代码生成表
     *
     * @param [tableCreateInput] 代码生成表创建输入
     * @return [TableDetailView]
     */
    fun createTable(tableCreateInput: TableCreateInput): TableDetailView {
        val table: Table = this.tableRepository.insert(tableCreateInput)
        return findTableById(table.id)
    }

    /**
     * 更新代码生成表通过ID
     *
     * @param [tableUpdateInput] 代码生成表更新输入
     * @return [TableDetailView]
     */
    fun updateTableById(tableUpdateInput: TableUpdateInput): TableDetailView {
        val table = this.tableRepository.update(tableUpdateInput)
        return findTableById(table.id)
    }

    /**
     * 删除代码生成表通过ID
     *
     * @param [id] ID
     */
    fun deleteTableById(id: Long) {
        this.tableRepository.deleteById(id)
    }

    fun generate(id: Long) {
        val table = this.tableRepository.findDetailById(id)
        defaultGeneratorTemplate.generator(table)
    }
}
