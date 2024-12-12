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
package com.zhengchalei.xadmin.modules.generator.template

import com.zhengchalei.xadmin.commons.util.StrUtil
import com.zhengchalei.xadmin.commons.util.StrUtil.convertToKebabCase
import com.zhengchalei.xadmin.commons.util.StrUtil.convertToPascalCase
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableDetailView
import freemarker.cache.ClassTemplateLoader
import freemarker.cache.TemplateLoader
import freemarker.template.Configuration
import freemarker.template.Template
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Paths
import org.springframework.stereotype.Service
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer

@Service
class DefaultGeneratorTemplate() {

    private val projectPath = "C:\\Users\\zhengchalei\\IdeaProjects\\xadmin"

    private val freemarkerConfiguration: FreeMarkerConfigurer = freemarkerGeneratorConfig()

    private final fun freemarkerGeneratorConfig(): FreeMarkerConfigurer {
        val configuration = Configuration(Configuration.VERSION_2_3_33)
        val templateLoader: TemplateLoader = ClassTemplateLoader(this.javaClass, "/templates/generator-templates")
        configuration.templateLoader = templateLoader
        val freeMarkerConfigurer = FreeMarkerConfigurer()
        freeMarkerConfigurer.configuration = configuration
        return freeMarkerConfigurer
    }

    fun generator(table: TableDetailView) {
        generatorDTO(table)
        generatorDomain(table)
        generatorRepository(table)
        generatorService(table)
        generatorController(table)
        generatorSQL(table)
    }

    private fun generatorDTO(table: TableDetailView) {
        val entityName = convertToPascalCase(table.tableName, table.tablePrefix)
        val filePath = generateDTOFilePath(table, table.module, "$entityName.dto")
        val dataModel = mapOf("table" to table, "StrUtil" to StrUtil)
        generateFile("dto.ftl", dataModel, filePath)
    }

    private fun generatorDomain(table: TableDetailView) {
        val entityName = convertToPascalCase(table.tableName, table.tablePrefix)
        val dataModel = mapOf("table" to table, "StrUtil" to StrUtil)
        val domainFilePath = generateFilePath(table, "domain", "$entityName.kt")
        generateFile("domain.ftl", dataModel, domainFilePath)
    }

    private fun generatorRepository(table: TableDetailView) {
        val entityName = convertToPascalCase(table.tableName, table.tablePrefix)
        val dataModel = mapOf("table" to table, "StrUtil" to StrUtil)
        val repositoryFilePath = generateFilePath(table, "repository", "${entityName}Repository.kt")
        generateFile("repository.ftl", dataModel, repositoryFilePath)
    }

    private fun generatorService(table: TableDetailView) {
        val entityName = convertToPascalCase(table.tableName, table.tablePrefix)
        val dataModel = mapOf("table" to table, "StrUtil" to StrUtil, "date" to java.time.LocalDate.now().toString())
        val serviceFilePath = generateFilePath(table, "service", "${entityName}Service.kt")
        generateFile("service.ftl", dataModel, serviceFilePath)
    }

    private fun generatorController(table: TableDetailView) {
        val entityName = convertToPascalCase(table.tableName, table.tablePrefix)
        val dataModel = mapOf("table" to table, "StrUtil" to StrUtil)
        val controllerFilePath = generateFilePath(table, "controller", "${entityName}Controller.kt")
        generateFile("controller.ftl", dataModel, controllerFilePath)
    }

    private fun generatorSQL(table: TableDetailView) {
        val filePath = generateResourceFilePath(table, "db.changelog-${table.tableName}.xml")
        val dataModel = mapOf("table" to table, "StrUtil" to StrUtil)
        generateFile("sql.ftl", dataModel, filePath)
    }

    // Helper method for file path generation
    private fun generateFilePath(table: TableDetailView, module: String, fileName: String): String {
        return Paths.get(
                projectPath,
                "src",
                "main",
                "kotlin",
                table.packageName.replace(".", File.separator),
                table.module,
                module,
                fileName,
            )
            .toString()
    }

    private fun generateDTOFilePath(table: TableDetailView, module: String, fileName: String): String {
        return Paths.get(
                projectPath,
                "src",
                "main",
                "dto",
                table.packageName.replace(".", File.separator),
                module,
                "domain",
                fileName,
            )
            .toString()
    }

    private fun generateResourceFilePath(table: TableDetailView, fileName: String): String {
        return Paths.get(projectPath, "src", "main", "resources", "db", "changelog", convertToKebabCase(fileName))
            .toString()
    }

    // Consolidated file generation method with UTF-8 encoding
    private fun generateFile(templateName: String, dataModel: Map<String, Any?>, outputPath: String) {
        val template: Template = freemarkerConfiguration.configuration.getTemplate(templateName)
        try {
            val file = File(outputPath)
            file.parentFile.mkdirs() // Ensure parent directories exist
            FileOutputStream(file).use { fos ->
                OutputStreamWriter(fos, StandardCharsets.UTF_8).use { writer -> template.process(dataModel, writer) }
            }
            println("File generated successfully: $outputPath")
        } catch (e: Exception) {
            println("Error generating file at $outputPath: ${e.message}")
        }
    }
}
