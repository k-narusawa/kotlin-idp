package com.knarusawa.demo.idp.idpdemo.domain.model.user

import com.knarusawa.demo.idp.idpdemo.configuration.SecurityConfig
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.record.UserRecord
import java.time.LocalDateTime

class User private constructor(
  val userId: UserId,
  var loginId: LoginId,
  var password: Password,
  var roles: List<Role>,
  var isLock: Boolean,
  var failedAttempts: Int?,
  var lockTime: LocalDateTime?,
  var isDisabled: Boolean,
) {
  companion object {
    fun of(loginId: String, password: String, roles: List<Role>) =
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

    fun from(userRecord: UserRecord) =
      User(
        userId = UserId(value = userRecord.userId),
        loginId = LoginId(value = userRecord.loginId),
        password = Password(value = userRecord.password),
        roles = userRecord.roles.map { Role.fromString(it) },
        isLock = userRecord.isLock,
        failedAttempts = userRecord.failedAttempts,
        lockTime = userRecord.lockTime,
        isDisabled = userRecord.isDisabled,
      )
  }

  fun updateLoginId(loginId: String) {
    this.loginId = LoginId(value = loginId)
  }

  fun updatePassword(password: String) {
    this.password = Password(value = SecurityConfig().passwordEncoder().encode(password))
  }
}
