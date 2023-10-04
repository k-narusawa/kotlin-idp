package com.knarusawa.idp.domain.model

import com.knarusawa.idp.domain.value.MessageId

data class MessageTemplateData(
        val messageId: MessageId,
        val subject: String,
        var body: String,
)