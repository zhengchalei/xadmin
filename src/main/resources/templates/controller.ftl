<#assign entityPascalCaseName = StrUtil.convertToPascalCase(table.tableName, table.tablePrefix)>
<#assign entityCamelCaseName = StrUtil.convertToCamelCase(table.tableName, table.tablePrefix)>
<#assign entityKebabCaseName = StrUtil.convertToKebabCase(table.tableName, table.tablePrefix)>
package ${table.packageName}.${table.module}.controller;

import com.zhengchalei.xadmin.commons.QueryPage;
import com.zhengchalei.xadmin.commons.R;
import com.zhengchalei.xadmin.modules.sys.domain.Log;
import com.zhengchalei.xadmin.modules.sys.domain.OperationType;
import ${table.packageName}.${table.module}.domain.*;
import ${table.packageName}.${table.module}.domain.dto.*;
import ${table.packageName}.${table.module}.service.${entityPascalCaseName}Service;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/${table.module}/${entityKebabCaseName}")
class ${entityPascalCaseName}Controller(val ${entityCamelCaseName}Service: ${entityPascalCaseName}Service) {

    @PreAuthorize("hasAuthority('${table.module}:${entityKebabCaseName}:read') or hasAnyRole('admin')")
    @GetMapping("/id/{id}")
    fun find${entityPascalCaseName}ById(@PathVariable id: Long): R<${entityPascalCaseName}DetailView> {
        val data = ${entityCamelCaseName}Service.find${entityPascalCaseName}ById(id)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('${table.module}:${entityKebabCaseName}:read') or hasAnyRole('admin')")
    @GetMapping("/list")
    fun find${entityPascalCaseName}List(@NotNull specification: ${entityPascalCaseName}ListSpecification): R<List<${entityPascalCaseName}PageView>> {
        val data = ${entityCamelCaseName}Service.find${entityPascalCaseName}List(specification)
        return R(data = data)
    }

    @PreAuthorize("hasAuthority('${table.module}:${entityKebabCaseName}:read') or hasAnyRole('admin')")
    @GetMapping("/page")
    fun find${entityPascalCaseName}Page(
        @NotNull specification: ${entityPascalCaseName}ListSpecification,
        @NotNull pageable: QueryPage,
    ): R<MutableList<${entityPascalCaseName}PageView>> {
        val data = ${entityCamelCaseName}Service.find${entityPascalCaseName}Page(specification, pageable.page())
        return R.success(data)
    }

    @Log(value = "创建${table.name}", type = OperationType.CREATE)
    @PreAuthorize("hasAuthority('${table.module}:${entityKebabCaseName}:create') or hasAnyRole('admin')")
    @PostMapping("/create")
    fun create${entityPascalCaseName}(
        @NotNull @RequestBody ${entityCamelCaseName}CreateInput: ${entityPascalCaseName}CreateInput
    ): R<${entityPascalCaseName}DetailView> {
        val data = ${entityCamelCaseName}Service.create${entityPascalCaseName}(${entityCamelCaseName}CreateInput)
        return R(data = data)
    }

    @Log(value = "修改${table.name}", type = OperationType.UPDATE)
    @PreAuthorize("hasAuthority('${table.module}:${entityKebabCaseName}:edit') or hasAnyRole('admin')")
    @PostMapping("/update")
    fun update${entityPascalCaseName}ById(
        @NotNull @RequestBody ${entityCamelCaseName}UpdateInput: ${entityPascalCaseName}UpdateInput
    ): R<${entityPascalCaseName}DetailView> {
        val data = ${entityCamelCaseName}Service.update${entityPascalCaseName}ById(${entityCamelCaseName}UpdateInput)
        return R(data = data)
    }

    @Log(value = "删除${table.name}", type = OperationType.DELETE)
    @PreAuthorize("hasAuthority('${table.module}:${entityKebabCaseName}:delete') or hasAnyRole('admin')")
    @DeleteMapping("/delete/{id}")
    fun delete${entityPascalCaseName}ById(@NotNull @PathVariable id: Long): R<Boolean> {
        ${entityCamelCaseName}Service.delete${entityPascalCaseName}ById(id)
        return R()
    }
}
