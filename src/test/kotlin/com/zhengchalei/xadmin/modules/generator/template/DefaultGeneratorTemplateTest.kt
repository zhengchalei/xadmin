package com.zhengchalei.xadmin.modules.generator.template

import com.zhengchalei.xadmin.modules.generator.domain.Table
import com.zhengchalei.xadmin.modules.generator.domain.dto.TableDetailView
import com.zhengchalei.xadmin.modules.generator.domain.id
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile

@Profile("dev")
@SpringBootTest
class DefaultGeneratorTemplateTest {

    @Autowired
    lateinit var sqlClient: KSqlClient

    @Autowired
    lateinit var defaultGeneratorTemplate: DefaultGeneratorTemplate

    @Test
    fun test() {
        this.sqlClient.createQuery(Table::class) {
            where(table.id eq 1)
            select(table.fetch(TableDetailView::class))
        }.fetchOne().let {
            this.defaultGeneratorTemplate.generator(it)
        }
    }

}