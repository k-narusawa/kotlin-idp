package com.knarusawa.demo.idp.idpdemo.domain.model.user

import com.knarusawa.demo.idp.idpdemo.configuration.SecurityConfig
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.record.UserRecord
import java.time.LocalDateTime

class User private constructor(
  userId: UserId,
  loginId: LoginId,
  password: Password,
  roles: List<Role>,
  isLock: Boolean,
  failedAttempts: Int,
  lockTime: LocalDateTime?,
  isDisabled: Boolean,
) {
  val userId: UserId = userId
  var loginId: LoginId = loginId
    private set
  var password: Password = password
    private set
  var roles: List<Role> = roles
    private set
  var isLock: Boolean = isLock
    private set
  var failedAttempts: Int = failedAttempts
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
        failedAttempts = 0,
        lockTime = null,
        isDisabled = false,
      )

    fun from(userRecord: UserRecord) = User(
      userId = UserId(value = userRecord.userId),
      loginId = LoginId(value = userRecord.loginId),
      password = Password(value = userRecord.password),
      roles = userRecord.roles.split(",").map { Role.fromString(it) },
      isLock = userRecord.isLock,
      failedAttempts = userRecord.failedAttempts,
      lockTime = userRecord.lockTime,
      isDisabled = userRecord.isDisabled,
    )
  }

  fun toEntity() = UserRecord(
    userId = this.userId.toString(),
    loginId = this.loginId.toString(),
    password = this.password.toString(),
    roles = this.roles.joinToString(","),
    isLock = this.isLock,
    failedAttempts = this.failedAttempts,
    lockTime = this.lockTime,
    isDisabled = this.isDisabled,
  )

  fun changeLoginId(loginId: String) {
    this.loginId = LoginId(value = loginId)
  }

  fun changePassword(password: String) {
    this.password = Password(value = SecurityConfig().passwordEncoder().encode(password))
  }

  fun authSuccess() {
    this.failedAttempts = 0
  }

  fun authFailed() {
    this.failedAttempts += 1
    if (!this.isLock && this.failedAttempts >= 5) { // TODO: ここのマジックナンバーを環境変数化したい
      this.isLock = true
      this.lockTime = LocalDateTime.now()
    }
  }

  fun unlockByTimeElapsed() {
    val isEnabledUnLocked =
      this.lockTime?.let { LocalDateTime.now().minusMinutes(30).isAfter(it) } ?: false

    if (this.isLock && isEnabledUnLocked) {
      this.isLock = false
      this.lockTime = null
    }
  }
}
