package com.zhengchalei.cloud.platform.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import com.zhengchalei.cloud.platform.config.WithMockTenantUser
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysDepartmentCreateInput
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysDepartmentUpdateInput
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation::class)
@WithMockTenantUser(
    username = "admin",
    authorities = ["ROLE_admin", "sys:department:create", "sys:department:update", "sys:department:delete", "sys:department:list", "sys:department:page", "sys:department:tree", "sys:department:tree-root", "sys:department:id"],
)
class SysDepartmentControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    companion object {
        var lastId = 1L
    }

    @Order(Integer.MIN_VALUE)
    @Test
    fun createSysDepartment() {
        val result =
            mockMvc
                .post("/api/sys/department/create") {
                    content =
                        objectMapper.writeValueAsString(
                            SysDepartmentCreateInput(
                                name = "测试部门",
                                description = "test",
                                sort = 1,
                                status = true,
                                parentId = 1,
                            ),
                        )
                    contentType = MediaType.APPLICATION_JSON
                }.andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        jsonPath("$.success") {
                            exists()
                            value(true)
                        }
                        jsonPath("$.data.id") {
                            exists()
                        }
                    }
                }.andReturn()

        lastId = JsonPath.parse(result.response.contentAsString).read("\$.data.id")
    }

    @Test
    fun updateSysDepartmentById() {
        mockMvc
            .post("/api/sys/department/update") {
                content =
                    objectMapper.writeValueAsString(
                        SysDepartmentUpdateInput(
                            id = 1,
                            name = "测试部门",
                            description = "修改测试",
                            sort = 1,
                            status = true,
                            parentId = null,
                        ),
                    )
                contentType = MediaType.APPLICATION_JSON
            }.andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$.success") {
                        exists()
                        value(true)
                    }
                }
            }
    }

    @Test
    fun findSysDepartmentList() {
        mockMvc
            .get("/api/sys/department/list")
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$.success") {
                        exists()
                        value(true)
                    }
                }
            }
    }

    @Test
    fun findSysDepartmentTreeRoot() {
        mockMvc
            .get("/api/sys/department/tree-root")
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$.success") {
                        exists()
                        value(true)
                    }
                }
            }
    }

    @Test
    fun findSysDepartmentPage() {
        mockMvc
            .get("/api/sys/department/page")
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$.success") {
                        exists()
                        value(true)
                    }
                }
            }
    }

    @Test
    fun findSysDepartmentTree() {
        mockMvc
            .get("/api/sys/department/tree")
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$.success") {
                        exists()
                        value(true)
                    }
                }
            }
    }

    @Test
    fun findSysDepartmentById() {
        mockMvc
            .get("/api/sys/department/id/$lastId")
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$.success") {
                        exists()
                        value(true)
                    }
                }
            }
    }

    @Order(Integer.MAX_VALUE)
    @Test
    fun deleteSysDepartmentById() {
        mockMvc
            .delete("/api/sys/department/delete/$lastId") {
                contentType = MediaType.APPLICATION_JSON
            }.andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$.success") {
                        exists()
                        value(true)
                    }
                }
            }
    }
}
