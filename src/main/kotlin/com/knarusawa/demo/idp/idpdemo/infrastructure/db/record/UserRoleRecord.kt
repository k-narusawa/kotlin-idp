package com.knarusawa.demo.idp.idpdemo.infrastructure.db.record

import com.knarusawa.demo.idp.idpdemo.domain.model.userRole.Role
import com.knarusawa.demo.idp.idpdemo.domain.model.userRole.UserRole
import java.time.LocalDateTime

data class UserRoleRecord(
  val roleId: Int?,
  val userId: String,
  val role: String,
  val createdAt: LocalDateTime?,
  val updatedAt: LocalDateTime?,
) {
  fun toUserRole() = UserRole(
    userId = this.userId,
    role = Role.fromString(this.role),
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
  )
}
