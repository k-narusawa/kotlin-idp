package com.knarusawa.idp.application.service.query

import com.knarusawa.idp.domain.model.messageTemplate.MessageId
import com.knarusawa.idp.domain.model.messageTemplate.MessageTemplateData

interface MessageTemplateRepository {
  fun findByMassageId(messageId: MessageId): MessageTemplateData
}