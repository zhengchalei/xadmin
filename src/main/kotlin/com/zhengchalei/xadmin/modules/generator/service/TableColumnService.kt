/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.generator.service

import com.zhengchalei.xadmin.modules.generator.domain.TableColumn
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableColumnCreateInput
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableColumnDetailView
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableColumnListSpecification
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableColumnUpdateInput
import com.zhengchalei.xadmin.modules.generator.repository.TableColumnRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 代码生成表服务
 *
 * @param [tableColumnRepository] 代码生成表存储库
 * @constructor 创建[TableColumnColumnService]
 * @author 郑查磊
 * @date 2024-08-17
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class TableColumnService(private val tableColumnRepository: TableColumnRepository) {
    private val logger = org.slf4j.LoggerFactory.getLogger(TableColumnService::class.java)

    /**
     * 查找代码生成表通过ID
     *
     * @param [id] ID
     * @return [TableColumnDetailView]
     */
    fun findTableColumnById(id: Long): TableColumnDetailView = this.tableColumnRepository.findDetailById(id)

    /**
     * 查找代码生成表列表
     *
     * @param [specification] 查询条件构造器
     */
    fun findTableColumnList(specification: TableColumnListSpecification) =
        this.tableColumnRepository.findList(specification)

    /**
     * 查找代码生成表分页
     *
     * @param [specification] 查询条件构造器
     * @param [pageable] 可分页
     */
    fun findTableColumnPage(specification: TableColumnListSpecification, pageable: Pageable) =
        this.tableColumnRepository.findPage(specification, pageable)

    /**
     * 创造代码生成表
     *
     * @param [tableColumnCreateInput] 代码生成表创建输入
     * @return [TableColumnDetailView]
     */
    fun createTableColumn(tableColumnCreateInput: TableColumnCreateInput): TableColumnDetailView {
        val tableColumn: TableColumn = this.tableColumnRepository.insert(tableColumnCreateInput)
        return findTableColumnById(tableColumn.id)
    }

    /**
     * 更新代码生成表通过ID
     *
     * @param [tableColumnUpdateInput] 代码生成表更新输入
     * @return [TableColumnDetailView]
     */
    fun updateTableColumnById(tableColumnUpdateInput: TableColumnUpdateInput): TableColumnDetailView {
        val tableColumn = this.tableColumnRepository.update(tableColumnUpdateInput)
        return findTableColumnById(tableColumn.id)
    }

    /**
     * 删除代码生成表通过ID
     *
     * @param [id] ID
     */
    fun deleteTableColumnById(id: Long) {
        this.tableColumnRepository.deleteById(id)
    }
}
