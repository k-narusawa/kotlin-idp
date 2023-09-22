package com.knarusawa.idp.infrastructure.adapter.db.repository.messageTemplate

import com.knarusawa.idp.application.service.query.MessageTemplateRepository
import com.knarusawa.idp.domain.model.error.ErrorCode
import com.knarusawa.idp.domain.model.error.IdpAppException
import com.knarusawa.idp.domain.model.messageTemplate.MessageId
import com.knarusawa.idp.domain.model.messageTemplate.MessageTemplateData
import org.springframework.stereotype.Repository

@Repository
class MessageTemplateRepositoryImpl : MessageTemplateRepository {
  override fun findByMassageId(messageId: MessageId): MessageTemplateData {
    val record = list.find { it.messageId == messageId.toString() }
      ?: throw IdpAppException(
        errorCode = ErrorCode.INTERNAL_SERVER_ERROR,
        logMessage = "メッセージテンプレートが見つかりません"
      )

    return MessageTemplateData(
      messageId = MessageId.from(record.messageId),
      subject = record.subject,
      body = record.body,
    )
  }

  private val list = listOf(
    MessageTemplateList(
      messageId = "MFA_MAIL_REGISTRATION",
      subject = "MFA認証の登録",
      body = """
        ワンタイムパスワード #{otp}
      """.trimIndent(),
    ),
    MessageTemplateList(
      messageId = "MFA_MAIL_AUTHENTICATION",
      subject = "ワンタイムパスワード認証",
      body = """
        ワンタイムパスワード #{otp}
      """.trimIndent(),
    ),
  )

  private data class MessageTemplateList(
    val messageId: String,
    val subject: String,
    val body: String,
  )
}