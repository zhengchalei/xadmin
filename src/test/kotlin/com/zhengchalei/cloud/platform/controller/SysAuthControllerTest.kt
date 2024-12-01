package com.zhengchalei.cloud.platform.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.zhengchalei.cloud.platform.commons.Const
import com.zhengchalei.cloud.platform.config.WithMockUser
import com.zhengchalei.cloud.platform.modules.sys.domain.dto.LoginDTO
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(OrderAnnotation::class)
class SysAuthControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun login() {
        mockMvc
            .post("/api/auth/login") {
                content =
                    objectMapper.writeValueAsString(
                        LoginDTO(
                            username = "admin",
                            password = "123456",
                            captcha = "1234",
                            captchaID = "",
                        ),
                    )
                contentType = MediaType.APPLICATION_JSON
            }.andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$.data.accessToken") { exists() }
                }
            }.andDo {
                log()
            }

        mockMvc
            .post("/api/auth/login") {
                content =
                    objectMapper.writeValueAsString(
                        LoginDTO(
                            username = "root",
                            password = "123456",
                            captcha = "1234",
                            captchaID = "",
                        ),
                    )
                contentType = MediaType.APPLICATION_JSON
            }.andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$.data.accessToken") { exists() }
                }
            }.andDo {
                log()
            }
    }

    @Test
    fun loginFail() {
        mockMvc
            .post("/api/auth/login") {
                content =
                    objectMapper.writeValueAsString(
                        LoginDTO(
                            username = "admin",
                            password = "1234561",
                            captcha = "1234",
                            captchaID = "",
                        ),
                    )
                contentType = MediaType.APPLICATION_JSON
            }.andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$.success") {
                        exists()
                        value(false)
                    }
                }
            }.andDo {
                log()
            }
    }

    @Test
    fun rootLoginLoginFail() {
        mockMvc
            .post("/api/auth/login") {
                content =
                    objectMapper.writeValueAsString(
                        LoginDTO(
                            username = "root",
                            password = "error password",
                            captcha = "1234",
                            captchaID = "",
                        ),
                    )
                contentType = MediaType.APPLICATION_JSON
            }.andExpect {
                status { isOk() }
                content {
                    contentType(MediaType.APPLICATION_JSON)
                    jsonPath("$.success") {
                        exists()
                        value(false)
                    }
                }
            }.andDo {
                log()
            }
    }

    @Test
    fun rootLogin() {
        mockMvc
            .post("/api/auth/login") {
                content =
                    objectMapper.writeValueAsString(
                        LoginDTO(
                            username = "root",
                            password = "123456",
                            captcha = "1234",
                            captchaID = "",
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
            }.andDo {
                log()
            }
    }

    @Test
    fun logout() {
    }

    @Test
    fun register() {
    }
}
