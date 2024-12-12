<#assign entityPascalCaseName = StrUtil.convertToPascalCase(table.tableName, table.tablePrefix)>
<#assign entityCamelCaseName = StrUtil.convertToCamelCase(table.tableName, table.tablePrefix)>
specification ${entityPascalCaseName}ListSpecification {
    <#list table.columns as column>
    eq(${StrUtil.convertToCamelCase(column.name, "")})
        <#if column.name == "create_time">
    ge(createTime) as startTime
    le(createTime) as endTime
        </#if>
    </#list>
}

${entityPascalCaseName}PageView {
    #allScalars
}

${entityPascalCaseName}DetailView {
    #allScalars
}

input ${entityPascalCaseName}CreateInput {
    #allScalars(this)
}

input ${entityPascalCaseName}UpdateInput {
    #allScalars(this)
    id!
}
