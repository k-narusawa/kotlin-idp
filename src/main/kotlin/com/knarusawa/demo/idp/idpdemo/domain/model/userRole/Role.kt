package com.knarusawa.demo.idp.idpdemo.domain.model.userRole

import com.knarusawa.demo.idp.idpdemo.domain.model.error.AppException
import com.knarusawa.demo.idp.idpdemo.domain.model.error.ErrorCode

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
        else -> throw AppException(
          errorCode = ErrorCode.BAD_REQUEST,
          errorMessage = "Role not found."
        )
      }
    }
  }
}