package com.knarusawa.idp.integration

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class ClientCredentialsFlowTest {
    companion object {
        private const val DUMMY_CLIENT_ID = "test"
        private const val DUMMY_CLIENT_SECRET = "test"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    @DisplayName("client_credentialsのフローでトークンを取得できる")
    fun clientCredentialsFlow() {
        mockMvc.perform(
                post("/oauth2/token")
                        .param("grant_type", "client_credentials")
                        .param("client_id", DUMMY_CLIENT_ID)
                        .param("client_secret", DUMMY_CLIENT_SECRET)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk)
                .andReturn()
    }

    @Test
    @DisplayName("client_credentialsのフローで取得したトークンをintrospectで有効性確認できること")
    fun clientCredentialsIntrospect() {
        val tokenResult = mockMvc.perform(
                post("/oauth2/token")
                        .param("grant_type", "client_credentials")
                        .param("client_id", "test")
                        .param("client_secret", "test")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk)
                .andReturn()

        val tokenResponse = objectMapper.readTree(tokenResult.response.contentAsString)
        val accessToken = tokenResponse.get("access_token").asText()

        val introspectResult = mockMvc.perform(post("/oauth2/introspect")
                .param("token", accessToken)
                .header(
                        "Authorization",
                        "Basic " + Base64.getEncoder()
                                .encodeToString("${DUMMY_CLIENT_ID}:${DUMMY_CLIENT_SECRET}".toByteArray())
                )
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk)
                .andReturn()

        val introspectResponse = objectMapper.readTree(introspectResult.response.contentAsString)
        val active = introspectResponse.get("active").asBoolean()

        assertThat(active).isTrue
    }
}
