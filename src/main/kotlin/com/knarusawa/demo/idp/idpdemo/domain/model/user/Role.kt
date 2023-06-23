package com.knarusawa.demo.idp.idpdemo.domain.model.user

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
        else -> throw IllegalArgumentException("Role not found.")
      }
    }
  }
}