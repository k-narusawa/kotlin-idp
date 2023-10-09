package com.knarusawa.idp.domain.value

enum class MessageId {
    MFA_MAIL_REGISTRATION,
    MFA_MAIL_AUTHENTICATION,
    TMP_USER_CONFIRM,
    TMP_USER_CONFIRM_FAILED,
    USER_REGISTER_COMPLETE,
    USER_LOGIN_ID_UPDATE,
    USER_LOGIN_ID_UPDATE_FAILED,
    ;

    companion object {
        fun from(str: String) = valueOf(str)
    }

    override fun toString(): String {
        return name
    }
}