package com.knarusawa.idp.domain.model.messageTemplate

data class MessageTemplateData(
        val messageId: MessageId,
        val subject: String,
        var body: String,
)