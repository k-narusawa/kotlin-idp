package com.knarusawa.demo.idp.idpdemo.domain.model.user

import com.knarusawa.demo.idp.idpdemo.configuration.SecurityConfig
import java.time.LocalDateTime

data class User(
    val userId: UserId,
    val loginId: LoginId,
    val password: Password,
    val roles: List<Role>,
    val isLock: Boolean,
    val failedAttempts: Int?,
    val lockTime: LocalDateTime?,
    val isDisabled: Boolean,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {
  companion object {
    fun new(loginId: String, password: String, roles: List<Role>) =
        User(
            userId = UserId.generate(),
            loginId = LoginId(value = loginId),
            password = Password(value = SecurityConfig().passwordEncoder().encode(password)),
            roles = roles,
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
      roles = roles,
      isLock = this.isLock,
      failedAttempts = this.failedAttempts,
      lockTime = this.lockTime,
      isDisabled = this.isDisabled,
      createdAt = this.createdAt,
      updatedAt = this.updatedAt
  )
}
