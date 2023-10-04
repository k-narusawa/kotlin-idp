package com.knarusawa.idp.application.service.query

import com.knarusawa.idp.domain.model.MessageTemplateData
import com.knarusawa.idp.domain.value.MessageId

interface MessageTemplateRepository {
    fun findByMassageId(messageId: MessageId): MessageTemplateData
}