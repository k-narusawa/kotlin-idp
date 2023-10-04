package com.knarusawa.idp.domain.model.messageTemplate

class MessageTemplate private constructor(
        val messageId: MessageId,
        val subject: String,
        val body: String,
) {

    companion object {
        fun of(messageTemplateData: MessageTemplateData, variables: List<Pair<String, String>>): MessageTemplate {
            var body = messageTemplateData.body

            val defaultVariables = listOf(
                    Pair("\${url}", ""),
            )

            (defaultVariables + variables).forEach {
                body = body.replace(it.first, it.second)
            }

            return MessageTemplate(
                    messageId = messageTemplateData.messageId,
                    subject = messageTemplateData.subject,
                    body = body
            )
        }
    }
}