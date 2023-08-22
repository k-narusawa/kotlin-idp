package com.knarusawa.demo.idp.idpdemo.domain.model.user

import com.knarusawa.demo.idp.idpdemo.configuration.SecurityConfig
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.record.UserRecord
import org.springframework.data.domain.AbstractAggregateRoot
import java.time.LocalDateTime

class User private constructor(
  val userId: UserId,
  loginId: LoginId,
  password: Password,
  roles: List<Role>,
  isLock: Boolean,
  failedAttempts: Int?,
  lockTime: LocalDateTime?,
  isDisabled: Boolean,
) : AbstractAggregateRoot<User>() {
  var loginId: LoginId = loginId
    private set
  var password: Password = password
    private set
  var roles: List<Role> = roles
    private set
  var isLock: Boolean = isLock
    private set
  var failedAttempts: Int? = failedAttempts
    private set
  var lockTime: LocalDateTime? = lockTime
    private set
  var isDisabled: Boolean = isDisabled
    private set

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
    registerEvent(UserEvent.UpdateEvent(user = this))
  }

  fun updatePassword(password: String) {
    this.password = Password(value = SecurityConfig().passwordEncoder().encode(password))
    registerEvent(UserEvent.UpdateEvent(user = this))
  }
}
