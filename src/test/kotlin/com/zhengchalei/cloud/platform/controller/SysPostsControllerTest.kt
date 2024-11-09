package com.zhengchalei.cloud.platform.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.JsonPath
import com.zhengchalei.cloud.platform.config.WithMockUser
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysPostsCreateInput
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.SysPostsUpdateInput
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
    authorities = ["ROLE_admin", "sys:posts:write", "sys:posts:edit", "sys:posts:delete", "sys:posts:read", "sys:posts:page", "sys:posts:read"],
)
class SysPostsControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    companion object {
        var lastId = 1L
    }

    @Order(Integer.MIN_VALUE)
    @Test
    fun createSysPosts() {
        val result =
            mockMvc
                .post("/api/sys/posts/create") {
                    content =
                        objectMapper.writeValueAsString(
                            SysPostsCreateInput(
                                name = "测试岗位",
                                status = true,
                                sort = 1,
                                description = "岗位",
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
    fun updateSysPostsById() {
        mockMvc
            .post("/api/sys/posts/update") {
                content =
                    objectMapper.writeValueAsString(
                        SysPostsUpdateInput(
                            id = lastId,
                            name = "测试岗位",
                            status = false,
                            sort = 1,
                            description = "岗位 update",
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
    fun findSysPostsList() {
        mockMvc
            .get("/api/sys/posts/list")
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
    fun findSysPostsPage() {
        mockMvc
            .get("/api/sys/posts/page")
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
    fun findSysPostsById() {
        mockMvc
            .get("/api/sys/posts/id/$lastId")
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
    fun deleteSysPostsById() {
        mockMvc
            .delete("/api/sys/posts/delete/$lastId") {
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
