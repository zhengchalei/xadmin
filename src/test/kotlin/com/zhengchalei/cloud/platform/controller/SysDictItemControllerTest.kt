package com.zhengchalei.cloud.platform.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import com.zhengchalei.cloud.platform.config.WithMockUser
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysDictItemCreateInput
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysDictItemUpdateInput
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
    authorities = ["ROLE_admin", "sys:dict-item:create", "sys:dict-item:update", "sys:dict-item:delete", "sys:dict-item:read", "sys:dict-item:page", "sys:dict-item:read"],
)
class SysDictItemControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    companion object {
        var lastId = 1L
    }

    @Order(Integer.MIN_VALUE)
    @Test
    fun createSysDictItem() {
        val result =
            mockMvc
                .post("/api/sys/dict-item/create") {
                    content =
                        objectMapper.writeValueAsString(
                            SysDictItemCreateInput(
                                name = "测试字典",
                                code = "test-dict",
                                description = "字典",
                                sort = 1,
                                status = true,
                                dictId = 1,
                                data = "data",
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
    fun updateSysDictItemById() {
        mockMvc
            .post("/api/sys/dict-item/update") {
                content =
                    objectMapper.writeValueAsString(
                        SysDictItemUpdateInput(
                            id = lastId,
                            name = "测试字典",
                            code = "test-dict",
                            description = "字典",
                            sort = 1,
                            status = true,
                            dictId = 1,
                            data = "data",
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
    fun findSysDictItemList() {
        mockMvc
            .get("/api/sys/dict-item/list")
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
    fun findSysDictItemPage() {
        mockMvc
            .get("/api/sys/dict-item/page")
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
    fun findSysDictItemById() {
        mockMvc
            .get("/api/sys/dict-item/id/$lastId")
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
    fun deleteSysDictItemById() {
        mockMvc
            .delete("/api/sys/dict-item/delete/$lastId") {
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
