<#assign entityPascalCaseName = StrUtil.convertToPascalCase(table.tableName, table.tablePrefix)>
<#assign entityCamelCaseName = StrUtil.convertToCamelCase(table.tableName, table.tablePrefix)>
package ${table.packageName}.${table.module}.repository

import ${table.packageName}.${table.module}.domain.dto.*
import ${table.packageName}.${table.module}.domain.*
import org.babyfish.jimmer.spring.repository.KRepository
import org.babyfish.jimmer.spring.repository.fetchSpringPage
import org.babyfish.jimmer.sql.kt.ast.expression.asc
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ${entityPascalCaseName}Repository : KRepository<${entityPascalCaseName}, Long> {

    fun findDetailById(id: Long) =
        sql.createQuery(${entityPascalCaseName}::class) {
            where(table.id eq id)
            select(table.fetch(${entityPascalCaseName}DetailView::class))
        }.fetchOne()

    fun findPage(specification: ${entityPascalCaseName}ListSpecification, pageable: Pageable): Page<${entityPascalCaseName}PageView> =
        sql.createQuery(${entityPascalCaseName}::class) {
            orderBy(table.id.asc())
            where(specification)
            select(table.fetch(${entityPascalCaseName}PageView::class))
        }.fetchSpringPage(pageable)

    fun findList(specification: ${entityPascalCaseName}ListSpecification): List<${entityPascalCaseName}PageView> =
        sql.createQuery(${entityPascalCaseName}::class) {
            orderBy(table.id.asc())
            where(specification)
            select(table.fetch(${entityPascalCaseName}PageView::class))
        }.execute()
}
