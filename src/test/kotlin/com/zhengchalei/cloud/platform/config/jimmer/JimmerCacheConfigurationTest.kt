package com.zhengchalei.cloud.platform.config.jimmer

import com.zhengchalei.cloud.platform.config.WithMockTenantUser
import com.zhengchalei.cloud.platform.modules.sys.domain.*
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.babyfish.jimmer.sql.kt.ast.expression.eq
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation::class)
@WithMockTenantUser()
class JimmerCacheConfigurationTest {

    private final val log = LoggerFactory.getLogger(JimmerCacheConfigurationTest::class.java)

    @Autowired
    lateinit var sqlClient: KSqlClient


    @Test
    fun test() {
        val randomName = System.currentTimeMillis().toString()
        // for 2
        val d1 = this.sqlClient
            .filters {
                disableByTypes(TenantFilter::class)
            }
            .createQuery(SysUser::class) {
                where(table.id eq 1)
                select(table.fetchBy {
                    allScalarFields()
                    department {
                        allScalarFields()
                    }
                    roles {
                        allScalarFields()
                    }
                })
            }.fetchOne()
        log.info("执行完首次 select")
        log.info("data: {}", d1)

        this.sqlClient.createUpdate(SysDepartment::class) {
            where(table.id eq d1.department?.id)
            set(table.name, randomName)
        }.execute()

        log.info("执行完修改")

        val department = this.sqlClient.createQuery(SysDepartment::class) {
            where(table.id eq d1.department?.id)
            select(table.fetchBy {
                allScalarFields()
            })
        }.fetchOne()
        log.info("修改后数据: {}", department)

        val d2 = this.sqlClient
            .filters {
                disableByTypes(TenantFilter::class)
            }
            .createQuery(SysUser::class) {
                where(table.id eq 1)
                select(table.fetchBy {
                    allScalarFields()
                    department {
                        allScalarFields()
                    }
                    roles {
                        allScalarFields()
                    }
                })
            }.fetchOne()
        log.info("data: {}", d2)

        // TODO， 这里事物 全部结束后才会被修改数据
        assertEquals(randomName, d2.department?.name)
    }

}