/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.generator.service

import com.zhengchalei.xadmin.modules.generator.domain.Table
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableCreateInput
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableDetailView
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableListSpecification
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableUpdateInput
import com.zhengchalei.xadmin.modules.generator.repository.TableRepository
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
class TableService(private val tableRepository: TableRepository) {
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
}
