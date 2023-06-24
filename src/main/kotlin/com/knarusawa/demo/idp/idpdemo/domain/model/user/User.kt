package com.knarusawa.demo.idp.idpdemo.domain.model.user

import com.knarusawa.demo.idp.idpdemo.configuration.SecurityConfig
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.RoleRecord
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.UserRecord
import java.time.LocalDateTime
import java.util.*

data class User(
  val userId: String,
  val loginId: LoginId,
  val password: Password,
  val isLock: Boolean,
  val failedAttempts: Int?,
  val lockTime: LocalDateTime?,
  val isDisabled: Boolean,
  val roles: List<Role>,
  val createdAt: LocalDateTime?,
  val updatedAt: LocalDateTime?
) {
  companion object {
    fun of(userRecord: UserRecord, roleEntities: List<RoleRecord>) =
      User(
        userId = userRecord.userId,
        loginId = LoginId(value = userRecord.loginId),
        password = Password(value = userRecord.password),
        isLock = userRecord.isLock,
        failedAttempts = userRecord.failedAttempts,
        lockTime = userRecord.lockTime,
        isDisabled = userRecord.isDisabled,
        roles = roleEntities.map { Role.fromString(it.role) },
        createdAt = userRecord.createdAt,
        updatedAt = userRecord.updatedAt
      )

    fun of(loginId: String, password: String, roles: List<String>) =
      User(
        userId = UUID.randomUUID().toString(),
        loginId = LoginId(value = loginId),
        password = Password(value = SecurityConfig().passwordEncoder().encode(password)),
        isLock = false,
        failedAttempts = null,
        lockTime = null,
        isDisabled = false,
        roles = roles.map { Role.fromString(it) },
        createdAt = null,
        updatedAt = null
      )
  }

  fun updateLoginId(loginId: String) = User(
    userId = this.userId,
    loginId = LoginId(value = loginId),
    password = this.password,
    isLock = this.isLock,
    failedAttempts = this.failedAttempts,
    lockTime = this.lockTime,
    isDisabled = this.isDisabled,
    roles = this.roles,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
  )
}
