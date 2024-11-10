package com.zhengchalei.cloud.platform.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.zhengchalei.cloud.platform.config.WithMockUser
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation::class)
@WithMockUser(
    username = "admin",
    authorities = ["ROLE_admin", "sys:department:create", "sys:department:edit", "sys:department:delete", "sys:department:read", "sys:department:read", "sys:department:read", "sys:department:read", "sys:department:read"],
)
class SysCurrentUserControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun currentUserInfo() {
        mockMvc
            .get("/api/user/info")
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
}
