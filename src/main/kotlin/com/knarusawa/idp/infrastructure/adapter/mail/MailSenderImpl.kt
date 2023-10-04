package com.knarusawa.idp.infrastructure.adapter.mail

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService
import com.amazonaws.services.simpleemail.model.*
import com.knarusawa.idp.configuration.Environments
import com.knarusawa.idp.domain.port.MailSender
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MailSenderImpl(
        private val simpleEmailService: AmazonSimpleEmailService,
        private val environments: Environments
) : MailSender {
    companion object {
        val logger = LoggerFactory.getLogger(MailSenderImpl::class.java)
    }

    override fun send(toAddress: String, subject: String, body: String) {
        val sendEmailRequest = SendEmailRequest()
                .withSource(environments.fromAddress)
                .withDestination(
                        Destination()
                                .withToAddresses(toAddress)
                )
                .withMessage(
                        Message()
                                .withSubject(
                                        Content().withCharset("UTF-8").withData(subject)
                                )
                                .withBody(
                                        Body()
                                                .withText(
                                                        Content()
                                                                .withCharset("UTF-8").withData(body)
                                                )
                                )
                )

        try {
            simpleEmailService.sendEmail(sendEmailRequest)
            logger.debug("メール送信に成功しました 送信先: $toAddress")
        } catch (e: Exception) {
            logger.error("メール送信に失敗しました e: $e")
            throw e
        }
    }
}