package com.zhengchalei.cloud.platform.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import com.zhengchalei.cloud.platform.config.WithMockTenantUser
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysPermissionCreateInput
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysPermissionUpdateInput
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
    authorities = ["ROLE_admin", "sys:permission:create", "sys:permission:update", "sys:permission:delete", "sys:permission:list", "sys:permission:tree-root", "sys:permission:page", "sys:permission:tree", "sys:permission:id"],
)
class SysPermissionControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    companion object {
        var lastId = 1L
    }

    @Order(Integer.MIN_VALUE)
    @Test
    fun createSysPermission() {
        val result =
            mockMvc
                .post("/api/sys/permission/create") {
                    content =
                        objectMapper.writeValueAsString(
                            SysPermissionCreateInput(
                                name = "测试权限",
                                code = "test-permission",
                                description = "权限",
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
                }.andReturn()

        lastId = JsonPath.parse(result.response.contentAsString).read("\$.data.id")
    }

    @Test
    fun updateSysPermissionById() {
        mockMvc
            .post("/api/sys/permission/update") {
                content =
                    objectMapper.writeValueAsString(
                        SysPermissionUpdateInput(
                            id = lastId,
                            name = "测试权限",
                            code = "test-permission",
                            description = "权限 update",
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
    fun findSysPermissionList() {
        mockMvc
            .get("/api/sys/permission/list")
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
    fun findSysPermissionTreeRoot() {
        mockMvc
            .get("/api/sys/permission/tree-root")
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
    fun findSysPermissionPage() {
        mockMvc
            .get("/api/sys/permission/page")
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
    fun findSysPermissionTree() {
        mockMvc
            .get("/api/sys/permission/tree")
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
    fun findSysPermissionById() {
        mockMvc
            .get("/api/sys/permission/id/$lastId")
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
    fun deleteSysPermissionById() {
        mockMvc
            .delete("/api/sys/permission/delete/$lastId") {
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
