package com.knarusawa.idp.domain.value

enum class ActivityType {
    LOGIN_SUCCESS,
    LOGIN_FAILED,
    LOGOUT,
    CHANGE_LOGIN_ID,
    CHANGE_PASSWORD,
    OTHER,
    ;

    companion object {
        fun from(activityType: String): ActivityType {
            return when (activityType) {
                "LOGIN_SUCCESS" -> LOGIN_SUCCESS
                "LOGIN_FAILED" -> LOGIN_FAILED
                "LOGOUT" -> LOGOUT
                "CHANGE_LOGIN_ID" -> CHANGE_LOGIN_ID
                "CHANGE_PASSWORD" -> CHANGE_PASSWORD
                else -> OTHER
            }
        }
    }

    override fun toString(): String {
        return this.name
    }
}
