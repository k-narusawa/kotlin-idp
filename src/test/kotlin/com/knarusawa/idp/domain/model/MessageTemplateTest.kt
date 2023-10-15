package com.knarusawa.idp.domain.model

import com.knarusawa.idp.domain.value.MessageId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MessageTemplateTest {

    @Test
    @DisplayName("メッセージテンプレートの変数の変換がされること")
    fun testOf() {
        val messageTemplateData = MessageTemplateData(
                messageId = MessageId.USER_LOGIN_ID_UPDATE,
                subject = "Test subject",
                body = "Hello #{name}, please click on #{url} to reset your password."
        )
        val variables = listOf(
                Pair("#{name}", "John"),
                Pair("#{url}", "https://example.com/reset-password")
        )
        val actual = MessageTemplate.of(messageTemplateData, variables).body

        assertThat(actual)
                .isEqualTo("Hello John, please click on https://example.com/reset-password to reset your password.")
    }

    @Test
    @DisplayName("メッセージテンプレートの変数に指定がない場合に文字列がそのまま表示されること")
    fun testOfWithNoCustomVariables() {
        val messageTemplateData = MessageTemplateData(
                messageId = MessageId.USER_LOGIN_ID_UPDATE,
                subject = "Test subject",
                body = "Hello, please click on #{url} to reset your password."
        )
        val variables = emptyList<Pair<String, String>>()

        val actual = MessageTemplate.of(messageTemplateData, variables).body

        assertThat(actual)
                .isEqualTo("Hello, please click on #{url} to reset your password.")
    }
}