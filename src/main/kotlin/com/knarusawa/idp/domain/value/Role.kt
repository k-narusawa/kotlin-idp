package com.knarusawa.idp.domain.value

import com.knarusawa.idp.domain.model.IdpAppException

enum class Role {
    ADMIN,
    USER
    ;

    companion object {
        fun items(): Map<String, Role> {
            return values().associateBy { it.name }
        }

        fun fromString(role: String): Role {
            return when (role) {
                "ADMIN" -> ADMIN
                "USER" -> USER
                else -> throw IdpAppException(
                        errorCode = ErrorCode.BAD_REQUEST,
                        logMessage = "Role not found."
                )
            }
        }
    }

    override fun toString(): String {
        return this.name
    }
}