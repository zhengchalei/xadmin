/*
 * 版权所有 © 2024 郑查磊.
 * 保留所有权利.
 *
 * 注意: 本文件受著作权法保护，未经授权不得复制或传播。
 */
package com.zhengchalei.xadmin.modules.generator.template

import com.zhengchalei.xadmin.modules.generator.domain.dto.TableDetailView
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service

@Service
class DefaultGeneratorTemplate {

    private val projectPath = "C:\\Users\\zhengchalei\\IdeaProjects\\xadmin"

    fun generator(table: TableDetailView) {
        generatorDTO(table)
        generatorDomain(table)
        generatorRepository(table)
        generatorService(table)
        generatorController(table)
    }

    private fun generatorDTO(table: TableDetailView) {
        val entityName = convertTableNameToClassName(table.tableName, table.tablePrefix)
        val dtoFileContent =
            """
            specification ${entityName}ListSpecification {
                eq(id)
                ge(createTime) as startTime
                le(createTime) as endTime
            }

            ${entityName}PageView {
                #allScalars
            }

            ${entityName}DetailView {
                #allScalars
            }

            input ${entityName}CreateInput {
                #allScalars(this)
            }

            input ${entityName}UpdateInput {
                #allScalars(this)
                id!
            }
        """
                .trimIndent()
        val dtoFilePath =
            Paths.get(
                projectPath,
                "src",
                "main",
                "dto",
                table.packageName.replace(".", File.separator),
                table.module,
                "domain",
                "$entityName.dto",
            )
        this.createFile(dtoFilePath, dtoFileContent)
    }

    private fun generatorDomain(table: TableDetailView) {
        val entityName = convertTableNameToClassName(table.tableName, table.tablePrefix)
        val columnsStr =
            table.columns
                .filter { it.columnName != "id" }
                .joinToString(separator = "\n    ") { column ->
                    val nullable = if (column.columnNullable) "" else "?"
                    "val ${column.columnName}: ${column.columnType}$nullable"
                }
        val domainFileContent =
            """
package ${table.packageName}.${table.module}.domain

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "${table.tableName}")
interface $entityName {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long

    $columnsStr
}
    """
                .trimIndent()

        val domainFilePath =
            Paths.get(
                projectPath,
                "src",
                "main",
                "kotlin",
                table.packageName.replace(".", File.separator),
                table.module,
                "domain",
                "$entityName.kt",
            )

        this.createFile(domainFilePath, domainFileContent)
    }

    private fun generatorRepository(table: TableDetailView) {
        val entityName = convertTableNameToClassName(table.tableName, table.tablePrefix)
        val repositoryFileContent =
            """
package ${table.packageName}.${table.module}.repository

import ${table.packageName}.${table.module}.domain.dto.*
import ${table.packageName}.${table.module}.domain.*
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ${entityName}Repository : KRepository<$entityName, Long> {
    fun findDetailById(id: Long) =
        sql.createQuery($entityName::class) {
                where(table.id eq id)
                select(table.fetch(${entityName}DetailView::class))
            }
            .fetchOne()

    fun findPage(specification: ${entityName}ListSpecification, pageable: Pageable): Page<${entityName}PageView> =
        sql.createQuery($entityName::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(${entityName}PageView::class))
            }
            .fetchSpringPage(pageable)

    fun findList(specification: ${entityName}ListSpecification): List<${entityName}PageView> =
        sql.createQuery($entityName::class) {
                orderBy(table.id.asc())
                where(specification)
                select(table.fetch(${entityName}PageView::class))
            }
            .execute()
}

        """
        val domainFilePath =
            Paths.get(
                projectPath,
                "src",
                "main",
                "kotlin",
                table.packageName.replace(".", File.separator),
                table.module,
                "repository",
                "${entityName}Repository.kt",
            )
        this.createFile(domainFilePath, repositoryFileContent)
    }

    private fun generatorService(table: TableDetailView) {
        val entityName = convertTableNameToClassName(table.tableName, table.tablePrefix)
        // 首字母小写
        val entityNameLowerCase = entityName.first().lowercase() + entityName.substring(1)

        val serviceFileContent =
            """
package ${table.packageName}.${table.module}.service

import ${table.packageName}.${table.module}.domain.*
import ${table.packageName}.${table.module}.domain.dto.*
import com.zhengchalei.xadmin.modules.sys.repository.${entityName}Repository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 *${table.name}服务
 *
 * @param [${entityNameLowerCase}Repository] 系统词典存储库
 * @constructor 创建[${entityName}Service]
 * @author 郑查磊
 * @date 2024-08-17
 */
@Service
@Transactional(rollbackFor = [Exception::class])
class ${entityName}Service(private val ${entityNameLowerCase}Repository: ${entityName}Repository) {
    private val logger = org.slf4j.LoggerFactory.getLogger(${entityName}Service::class.java)

    /**
     * 查找${table.name}通过ID
     *
     * @param [id] ID
     * @return [${entityName}DetailView]
     */
    fun find${entityName}ById(id: Long): ${entityName}DetailView = this.${entityNameLowerCase}Repository.findDetailById(id)

    /**
     * 查找${table.name}列表
     *
     * @param [specification] 查询条件构造器
     */
    fun find${entityName}List(specification: ${entityName}ListSpecification) =
        this.${entityNameLowerCase}Repository.findList(specification)

    /**
     * 查找${table.name}分页
     *
     * @param [specification] 查询条件构造器
     * @param [pageable] 可分页
     */
    fun find${entityName}Page(specification: ${entityName}ListSpecification, pageable: Pageable) =
        this.${entityNameLowerCase}Repository.findPage(specification, pageable)

    /**
     * 创建${table.name}
     *
     * @param [${entityNameLowerCase}CreateInput] ${table.name}创建输入
     * @return [${entityName}DetailView]
     */
    fun create${entityName}(${entityNameLowerCase}CreateInput: ${entityName}CreateInput): ${entityName}DetailView {
        val ${entityNameLowerCase}: ${entityName} = this.${entityNameLowerCase}Repository.insert(${entityNameLowerCase}CreateInput)
        return find${entityName}ById(${entityNameLowerCase}.id)
    }

    /**
     * 更新${table.name}通过ID
     *
     * @param [${entityNameLowerCase}UpdateInput] ${table.name}更新输入
     * @return [${entityName}DetailView]
     */
    fun update${entityName}ById(${entityNameLowerCase}UpdateInput: ${entityName}UpdateInput): ${entityName}DetailView {
        val ${entityNameLowerCase} = this.${entityNameLowerCase}Repository.update(${entityNameLowerCase}UpdateInput)
        return find${entityName}ById(${entityNameLowerCase}.id)
    }

    /**
     * 删除${table.name}通过ID
     *
     * @param [id] ID
     */
    fun delete${entityName}ById(id: Long) = this.${entityNameLowerCase}Repository.deleteById(id)
}
        """
        val domainFilePath =
            Paths.get(
                projectPath,
                "src",
                "main",
                "kotlin",
                table.packageName.replace(".", File.separator),
                table.module,
                "service",
                "${entityName}Service.kt",
            )
        this.createFile(domainFilePath, serviceFileContent)
    }

    private fun generatorController(table: TableDetailView) {
        val entityName = convertTableNameToClassName(table.tableName, table.tablePrefix)
        val pathName = convertPathName(table.tableName, table.tablePrefix)
        // 首字母小写
        val entityNameLowerCase = entityName.first().lowercase() + entityName.substring(1)
        val controllerFileContent =
            """
package ${table.packageName}.${table.module}.controller

import com.zhengchalei.xadmin.commons.QueryPage
import com.zhengchalei.xadmin.commons.R
import ${table.packageName}.${table.module}.domain.Log
import ${table.packageName}.${table.module}.domain.OperationType
import ${table.packageName}.${table.module}.domain.dto.*
import ${table.packageName}.${table.module}.service.${entityName}Service
import jakarta.validation.constraints.NotNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/api/${table.module}/${pathName}")
class ${entityName}Controller(val ${entityNameLowerCase}Service: ${entityName}Service) {
    @PreAuthorize("hasAuthority('${table.module}:${pathName}:read') or hasAnyRole('admin')")
    @GetMapping("/id/{id}")
    fun find${entityName}ById(@PathVariable id: Long): R<${entityName}DetailView> {
        val data = ${entityNameLowerCase}Service.find${entityName}ById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('${table.module}:${pathName}:read') or hasAnyRole('admin')")
    @GetMapping("/list")
    fun find${entityName}List(@NotNull specification: ${entityName}ListSpecification): R<List<${entityName}PageView>> {
        val data = ${entityNameLowerCase}Service.find${entityName}List(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('${table.module}:${pathName}:read') or hasAnyRole('admin')")
    @GetMapping("/page")
    fun find${entityName}Page(
        @NotNull specification: ${entityName}ListSpecification,
        @NotNull pageable: QueryPage,
    ): R<MutableList<${entityName}PageView>> {
        val data = ${entityNameLowerCase}Service.find${entityName}Page(specification, pageable.page())
        return R.success(data)
    }

    @Log(value = "创建${table.name}", type = OperationType.CREATE)
    @PreAuthorize("hasAuthority('${table.module}:${pathName}:create') or hasAnyRole('admin')")
    @PostMapping("/create")
    fun create${entityName}(
        @NotNull @RequestBody ${entityNameLowerCase}CreateInput: ${entityName}CreateInput
    ): R<${entityName}DetailView> {
        val data = ${entityNameLowerCase}Service.create${entityName}(${entityNameLowerCase}CreateInput)
        return R(data = data)
    }

    @Log(value = "修改${table.name}", type = OperationType.UPDATE)
    @PreAuthorize("hasAuthority('${table.module}:${pathName}:edit') or hasAnyRole('admin')")
    @PostMapping("/update")
    fun update${entityName}ById(
        @NotNull @RequestBody ${entityNameLowerCase}UpdateInput: ${entityName}UpdateInput
    ): R<${entityName}DetailView> {
        val data = ${entityNameLowerCase}Service.update${entityName}ById(${entityNameLowerCase}UpdateInput)
        return R(data = data)
    }

    @Log(value = "删除${table.name}", type = OperationType.DELETE)
    @PreAuthorize("hasAuthority('${table.module}:${pathName}:delete') or hasAnyRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun delete${entityName}ById(@NotNull @PathVariable id: Long): R<Boolean> {
        ${entityNameLowerCase}Service.delete${entityName}ById(id)
        return R()
    }
}
            """
        val controllerFilePath =
            Paths.get(
                projectPath,
                "src",
                "main",
                "kotlin",
                table.packageName.replace(".", File.separator),
                table.module,
                "controller",
                "${entityName}Controller.kt",
            )
        this.createFile(controllerFilePath, controllerFileContent)
    }

    private fun createFile(filePath: java.nio.file.Path, content: String) {
        try {
            Files.createDirectories(filePath.parent) // 确保目录存在
            Files.writeString(filePath, content) // 写入文件内容
            println("文件生成成功: $filePath")
        } catch (e: Exception) {
            println("文件生成失败: ${e.message}")
        }
    }

    private fun convertTableNameToClassName(tableName: String, tablePrefix: String?): String {
        if (tableName.isBlank()) return ""
        val nameWithoutPrefix =
            tablePrefix?.takeIf { it.isNotEmpty() && tableName.startsWith(it) }?.let { tableName.removePrefix(it) }
                ?: tableName
        return nameWithoutPrefix.split("_").filter { it.isNotEmpty() }.joinToString("") { StringUtils.capitalize(it) }
    }

    private fun convertPathName(tableName: String, tablePrefix: String?): String {
        if (tableName.isBlank()) return ""
        val nameWithoutPrefix =
            tablePrefix?.takeIf { it.isNotEmpty() && tableName.startsWith(it) }?.let { tableName.removePrefix(it) }
                ?: tableName
        return nameWithoutPrefix.split("_").filter { it.isNotEmpty() }.joinToString("-") { StringUtils.capitalize(it) }
    }
}
