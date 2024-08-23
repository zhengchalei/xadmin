package com.zhengchalei.cloud.platform.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import com.zhengchalei.cloud.platform.config.WithMockTenantUser
import com.zhengchalei.cloud.platform.modules.sys.domain.Gender
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysUserCreateInput
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysUserUpdateInput
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
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation::class)
@WithMockTenantUser(
    username = "admin",
    authorities = ["ROLE_admin", "sys:user:create", "sys:user:update", "sys:user:delete", "sys:user:list", "sys:user:page", "sys:user:id"]
)
class SysUserControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    companion object {
        var lastId = 1L
    }

    @Order(Integer.MIN_VALUE)
    @Test
    fun createSysUser() {
        val result = mockMvc.post("/api/sys/user/create") {
            content = objectMapper.writeValueAsString(
                SysUserCreateInput(
                    username = "xiaoshitou",
                    email = "xiaoshitou@163.com",
                    status = true,
                    roleIds = listOf(1, 2),
                    birthday = LocalDate.now(),
                    gender = Gender.MALE,
                    phoneNumber = "13800000000",
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
    fun createSysUserAdmin() {
        mockMvc.post("/api/sys/user/create") {
            content = objectMapper.writeValueAsString(
                SysUserCreateInput(
                    username = "admin",
                    email = "xiaoshitou@163.com",
                    status = true,
                    roleIds = listOf(1, 2),
                    birthday = LocalDate.now(),
                    gender = Gender.MALE,
                    phoneNumber = "13800000000",
                )
            )
            contentType = MediaType.APPLICATION_JSON
        }
            .andExpect {
                status { isOk() }
                content {
                    jsonPath("$.success") {
                        exists()
                        value(false)
                    }
                }
            }
    }

    @Test
    fun updateSysUserById() {
        mockMvc.post("/api/sys/user/update") {
            content = objectMapper.writeValueAsString(
                SysUserUpdateInput(
                    id = lastId,
                    username = "xiaoshitou",
                    email = "xiaoshitou@163.com",
                    status = true,
                    roleIds = listOf(1, 2),
                    birthday = LocalDate.now(),
                    gender = Gender.FEMALE,
                    phoneNumber = "13800000000",
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
    fun updateAdmin() {
        mockMvc.post("/api/sys/user/update") {
            content = objectMapper.writeValueAsString(
                SysUserUpdateInput(
                    id = 1,
                    username = "xiaoshitou",
                    email = "xiaoshitou@163.com",
                    status = true,
                    roleIds = listOf(1, 2),
                    birthday = LocalDate.now(),
                    gender = Gender.FEMALE,
                    phoneNumber = "13800000000",
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
                        value(false)
                    }
                }
            }
    }

    @Test
    fun findSysUserList() {
        mockMvc.get("/api/sys/user/list")
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
    fun findSysUserPage() {
        mockMvc.get("/api/sys/user/page")
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
    fun findSysUserById() {
        mockMvc.get("/api/sys/user/id/$lastId")
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
    fun deleteSysUserById() {
        mockMvc.delete("/api/sys/user/delete/$lastId") {
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
    fun deleteSysUserAdminById() {
        mockMvc.delete("/api/sys/user/delete/1") {
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