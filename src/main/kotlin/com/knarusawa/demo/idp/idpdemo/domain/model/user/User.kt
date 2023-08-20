package com.knarusawa.demo.idp.idpdemo.domain.model.user

import com.knarusawa.demo.idp.idpdemo.configuration.SecurityConfig
import java.time.LocalDateTime

class User(
  var userId: UserId,
  var loginId: LoginId,
  var password: Password,
  var roles: List<Role>,
  var isLock: Boolean,
  var failedAttempts: Int?,
  var lockTime: LocalDateTime?,
  var isDisabled: Boolean,
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
      )
  }

  fun updateLoginId(loginId: String) {
    this.loginId = LoginId(value = loginId)
  }

  fun updatePassword(password: String) {
    this.password = Password(value = SecurityConfig().passwordEncoder().encode(password))
  }
}
