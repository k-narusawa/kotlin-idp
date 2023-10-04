package com.knarusawa.idp.application.facade

import com.knarusawa.idp.application.service.query.MessageTemplateRepository
import com.knarusawa.idp.domain.model.messageTemplate.MessageId
import com.knarusawa.idp.domain.model.messageTemplate.MessageTemplate
import com.knarusawa.idp.domain.port.MailSender
import org.springframework.stereotype.Component

@Component
class MassageSenderFacade(
        private val messageTemplateRepository: MessageTemplateRepository,
        private val mailSender: MailSender
) {
    fun exec(toAddress: String, messageId: MessageId, variables: List<Pair<String, String>> = listOf()) {
        val data = messageTemplateRepository.findByMassageId(messageId)

        val messageTemplate = MessageTemplate.of(data, variables)

        mailSender.send(to = toAddress, subject = messageTemplate.subject, body = messageTemplate.body)
    }
}