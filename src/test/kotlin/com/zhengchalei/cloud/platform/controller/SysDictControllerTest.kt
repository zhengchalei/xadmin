package com.zhengchalei.cloud.platform.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import com.zhengchalei.cloud.platform.config.WithMockUser
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysDictCreateInput
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysDictUpdateInput
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
@WithMockUser(
    username = "admin",
    authorities = ["ROLE_admin", "sys:dict:create", "sys:dict:edit", "sys:dict:delete", "sys:dict:read", "sys:dict:page", "sys:dict:read"],
)
class SysDictControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    companion object {
        var lastId = 1L
    }

    @Order(Integer.MIN_VALUE)
    @Test
    fun createSysDict() {
        val result =
            mockMvc
                .post("/api/sys/dict/create") {
                    content =
                        objectMapper.writeValueAsString(
                            SysDictCreateInput(
                                name = "测试字典",
                                code = "test-dict",
                                description = "字典",
                                sort = 1,
                                status = true,
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
    fun updateSysDictById() {
        mockMvc
            .post("/api/sys/dict/update") {
                content =
                    objectMapper.writeValueAsString(
                        SysDictUpdateInput(
                            id = lastId,
                            name = "测试字典",
                            code = "test-dict-update",
                            description = "字典",
                            sort = 1,
                            status = false,
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
    fun findSysDictList() {
        mockMvc
            .get("/api/sys/dict/list")
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
    fun findSysDictPage() {
        mockMvc
            .get("/api/sys/dict/page")
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
    fun findSysDictById() {
        mockMvc
            .get("/api/sys/dict/id/$lastId")
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
    fun deleteSysDictById() {
        mockMvc
            .delete("/api/sys/dict/delete/$lastId") {
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
