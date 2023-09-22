package com.knarusawa.idp.domain.model.messageTemplate

enum class MessageId {
  MFA_MAIL_REGISTRATION,
  MFA_MAIL_AUTHENTICATION,
  ;

  companion object {
    fun from(str: String) = valueOf(str)
  }
}