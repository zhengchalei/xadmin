<?xml version="1.1" encoding="UTF-8" standalone="no"?>
<databaseChangeLog xmlns="http://www.liquibase.org/xml/ns/dbchangelog"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-latest.xsd">
    <changeSet id="创建${table.name}表" author="郑查磊">
        <createTable tableName="${table.tableName}">
            <column autoIncrement="true" name="id" type="BIGINT">
                <constraints nullable="false" primaryKey="true"/>
            </column>
            <#list table.columns as column>
                <column name="${column.type}"
                        remarks="${column.comment}" <#if column.defaultValue??> defaultValueComputed="${column.defaultValue}"</#if>
                        <#if column.type == "String">
                            type="text"
                        <#elseif column.type == "Long">
                            type="bigint"
                        <#elseif column.type == "Int">
                            type="int"
                        <#elseif column.type == "DateTime">
                            type="timestamp"
                        <#else>
                            type="text"
                        </#if>
                >
                    <#if !column.nullable>
                        <constraints nullable="false" <#if column.unique>  unique="true"  </#if> />
                    </#if>
                </column>
            </#list>

            <column defaultValueComputed="CURRENT_TIMESTAMP" name="create_time" remarks="上传时间" type="timestamp">
                <constraints nullable="false"/>
            </column>
            <column defaultValueComputed="CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP" name="update_time"
                    remarks="更新时间" type="timestamp">
                <constraints nullable="false"/>
            </column>
            <column name="create_by" remarks="创建者" type="BIGINT"/>
            <column name="update_by" remarks="更新者" type="BIGINT"/>
        </createTable>
        <addForeignKeyConstraint baseColumnNames="create_by" baseTableName="${table.tableName}"
                                 constraintName="${table.tableName}_create_by"
                                 referencedColumnNames="id" referencedTableName="sys_user"/>
        <addForeignKeyConstraint baseColumnNames="update_by" baseTableName="${table.tableName}"
                                 constraintName="${table.tableName}_update_by"
                                 referencedColumnNames="id" referencedTableName="sys_user"/>
    </changeSet>
</databaseChangeLog>
