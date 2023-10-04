package com.knarusawa.idp.domain.model.messageTemplate

enum class MessageId {
    MFA_MAIL_REGISTRATION,
    MFA_MAIL_AUTHENTICATION,
    TMP_USER_CONFIRM,
    TMP_USER_CONFIRM_FAILED,
    USER_REGISTER_COMPLETE,
    ;

    companion object {
        fun from(str: String) = valueOf(str)
    }
}