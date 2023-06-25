package com.knarusawa.demo.idp.idpdemo.domain.model.userRole

import java.time.LocalDateTime

data class UserRole(
  val userId: String,
  val role: Role,
  val createdAt: LocalDateTime?,
  val updatedAt: LocalDateTime?
) {
  companion object {
    fun of(userId: String, role: Role) = UserRole(
      userId = userId,
      role = role,
      createdAt = null,
      updatedAt = null
    )
  }
}
