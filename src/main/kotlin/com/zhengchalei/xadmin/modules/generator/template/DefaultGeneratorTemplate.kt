/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.generator.template

import com.zhengchalei.xadmin.modules.generator.domain.Table
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableDetailView
import org.springframework.stereotype.Service

@Service
class DefaultGeneratorTemplate {

    fun generator(table: TableDetailView) {}

    private fun generatorDomain(table: Table) {}

    private fun generatorRepository(table: Table) {}

    private fun generatorService(table: Table) {}

    private fun generatorController(table: Table) {}
}
