<#assign entityPascalCaseName = StrUtil.convertToPascalCase(table.tableName, table.tablePrefix)>
<#assign entityCamelCaseName = StrUtil.convertToCamelCase(table.tableName, table.tablePrefix)>
package ${table.packageName}.${table.module}.domain

import org.babyfish.jimmer.sql.*

@Entity
@Table(name = "${entityPascalCaseName}")
interface ${entityPascalCaseName} {
<#list table.columns as column>
    <#if column.name == "id">
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long
    <#else>
        <#if column.unique>
        @Key
        </#if>
        val ${StrUtil.convertToCamelCase(column.name, "")}: ${column.type}<#if column.nullable>?</#if>
    </#if>
</#list>
}
