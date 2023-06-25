package com.knarusawa.demo.idp.idpdemo.domain.model.user

import com.knarusawa.demo.idp.idpdemo.configuration.SecurityConfig
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
  val createdAt: LocalDateTime?,
  val updatedAt: LocalDateTime?
) {
  companion object {
    fun of(loginId: String, password: String) =
      User(
        userId = UUID.randomUUID().toString(),
        loginId = LoginId(value = loginId),
        password = Password(value = SecurityConfig().passwordEncoder().encode(password)),
        isLock = false,
        failedAttempts = null,
        lockTime = null,
        isDisabled = false,
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
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
  )
}
