package com.zhengchalei.cloud.platform.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import com.zhengchalei.cloud.platform.config.WithMockTenantUser
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysRoleCreateInput
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysRoleUpdateInput
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
    authorities = ["ROLE_admin", "sys:role:create", "sys:role:update", "sys:role:delete", "sys:role:list", "sys:role:page", "sys:role:id"]
)
class SysRoleControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    companion object {
        var lastId = 1L
    }

    @Order(Integer.MIN_VALUE)
    @Test
    fun createSysRole() {
        val result = mockMvc.post("/api/sys/role/create") {
            content = objectMapper.writeValueAsString(
                SysRoleCreateInput(
                    name = "测试角色",
                    code = "test-role",
                    description = "角色",
                    permissionIds = listOf(1, 2, 3)
                )
            )
            contentType = MediaType.APPLICATION_JSON
        }
            .andExpect {
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
    fun updateSysRoleById() {
        mockMvc.post("/api/sys/role/update") {
            content = objectMapper.writeValueAsString(
                SysRoleUpdateInput(
                    id = lastId,
                    name = "测试角色",
                    code = "test-role",
                    description = "角色 update",
                    permissionIds = listOf(1, 2, 3)
                )
            )
            contentType = MediaType.APPLICATION_JSON
        }
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
    fun findSysRoleList() {
        mockMvc.get("/api/sys/role/list")
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
    fun findSysRolePage() {
        mockMvc.get("/api/sys/role/page")
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
    fun findSysRoleById() {
        mockMvc.get("/api/sys/role/id/$lastId")
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
    fun deleteSysRoleById() {
        mockMvc.delete("/api/sys/role/delete/$lastId") {
            contentType = MediaType.APPLICATION_JSON
        }
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
    fun deleteSysRoleAdminById() {
        mockMvc.delete("/api/sys/role/delete/1") {
            contentType = MediaType.APPLICATION_JSON
        }
            .andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$.success") {
                        exists()
                        value(false)
                    }
                }
            }
    }

}