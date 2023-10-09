package com.knarusawa.idp.application.facade

import com.knarusawa.idp.application.service.query.MessageTemplateRepository
import com.knarusawa.idp.domain.model.MessageTemplate
import com.knarusawa.idp.domain.port.MailSender
import com.knarusawa.idp.domain.value.MessageId
import com.knarusawa.idp.infrastructure.middleware.logger
import org.springframework.stereotype.Component

@Component
class MassageSenderFacade(
        private val messageTemplateRepository: MessageTemplateRepository,
        private val mailSender: MailSender
) {
    val log = logger()
    fun exec(toAddress: String, messageId: MessageId, variables: List<Pair<String, String>> = listOf()) {
        val data = messageTemplateRepository.findByMassageId(messageId)

        val messageTemplate = MessageTemplate.of(data, variables)

        log.info("メッセージ送信 メッセージID:[${messageId}]")
        mailSender.send(to = toAddress, subject = messageTemplate.subject, body = messageTemplate.body)
    }
}