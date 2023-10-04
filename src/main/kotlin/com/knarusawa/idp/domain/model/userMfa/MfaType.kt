package com.knarusawa.idp.domain.model.userMfa

enum class MfaType {
    MAIL,
    SMS,
    APP,
    ;

    override fun toString(): String {
        return this.name
    }

    companion object {
        fun from(type: String): MfaType {
            return when (type) {
                "MAIL" -> MAIL
                "SMS" -> SMS
                else -> APP
            }
        }
    }
}