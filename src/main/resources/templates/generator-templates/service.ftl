<#assign entityPascalCaseName = StrUtil.convertToPascalCase(table.tableName, table.tablePrefix)>
<#assign entityCamelCaseName = StrUtil.convertToCamelCase(table.tableName, table.tablePrefix)>
package ${table.packageName}.${table.module}.service

import ${table.packageName}.${table.module}.domain.*
import ${table.packageName}.${table.module}.domain.dto.*
import com.zhengchalei.xadmin.modules.sys.repository.${entityPascalCaseName}Repository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.slf4j.LoggerFactory

/**
 * ${table.name}服务
 *
 * @param [${entityCamelCaseName}Repository] {tableName}Repository
 * @constructor 创建[${entityPascalCaseName}Service]
 * @author 郑查磊
 * @date ${date}
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class ${entityPascalCaseName}Service(private val ${entityCamelCaseName}Repository: ${entityPascalCaseName}Repository) {

    private val logger = LoggerFactory.getLogger(${entityPascalCaseName}Service::class.java)

    /**
     * 查找${table.name}通过ID
     *
     * @param [id] ID
     * @return [${entityPascalCaseName}DetailView]
     */
    fun find${entityPascalCaseName}ById(id: Long): ${entityPascalCaseName}DetailView =
        ${entityCamelCaseName}Repository.findDetailById(id)

    /**
     * 查找${table.name}列表
     *
     * @param [specification] 查询条件构造器
     */
    fun find${entityPascalCaseName}List(specification: ${entityPascalCaseName}ListSpecification) =
        ${entityCamelCaseName}Repository.findList(specification)

    /**
     * 查找${table.name}分页
     *
     * @param [specification] 查询条件构造器
     * @param [pageable] 可分页
     */
    fun find${entityPascalCaseName}Page(specification: ${entityPascalCaseName}ListSpecification, pageable: Pageable) =
        ${entityCamelCaseName}Repository.findPage(specification, pageable)

    /**
     * 创建${table.name}
     *
     * @param [${entityCamelCaseName}CreateInput] ${table.name}创建输入
     * @return [${entityPascalCaseName}DetailView]
     */
    fun create${entityPascalCaseName}(${entityCamelCaseName}CreateInput: ${entityPascalCaseName}CreateInput): ${entityPascalCaseName}DetailView {
        val ${entityCamelCaseName}: ${entityPascalCaseName} = ${entityCamelCaseName}Repository.insert(${entityCamelCaseName}CreateInput)
        return find${entityPascalCaseName}ById(${entityCamelCaseName}.id)
    }

    /**
     * 更新${table.name}通过ID
     *
     * @param [${entityCamelCaseName}UpdateInput] ${table.name}更新输入
     * @return [${entityPascalCaseName}DetailView]
     */
    fun update${entityPascalCaseName}ById(${entityCamelCaseName}UpdateInput: ${entityPascalCaseName}UpdateInput): ${entityPascalCaseName}DetailView {
        val ${entityCamelCaseName} = ${entityCamelCaseName}Repository.update(${entityCamelCaseName}UpdateInput)
        return find${entityPascalCaseName}ById(${entityCamelCaseName}.id)
    }

    /**
     * 删除${table.name}通过ID
     *
     * @param [id] ID
     */
    fun delete${entityPascalCaseName}ById(id: Long) = ${entityCamelCaseName}Repository.deleteById(id)
}
