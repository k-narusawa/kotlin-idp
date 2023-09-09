package com.knarusawa.idp.domain.model.user

import com.knarusawa.idp.configuration.SecurityConfig
import com.knarusawa.idp.infrastructure.adapter.db.record.UserRecord
import java.time.LocalDateTime

class User private constructor(
  userId: UserId,
  loginId: LoginId,
  password: Password,
  roles: List<Role>,
  isUsingMfa: Boolean,
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
  var isUsingMfa: Boolean = isUsingMfa
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
    private const val MAX_LOGIN_ATTEMPTS = 5
    private const val AUTO_UNLOCK_DURATION_MIN = 30

    fun of(loginId: String, password: String, roles: List<Role>) =
      User(
        userId = UserId.generate(),
        loginId = LoginId(value = loginId),
        password = Password(value = SecurityConfig().passwordEncoder().encode(password)),
        roles = roles,
        isUsingMfa = false,
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
      isUsingMfa = userRecord.isUsingMfa,
      isLock = userRecord.isLock,
      failedAttempts = userRecord.failedAttempts,
      lockTime = userRecord.lockTime,
      isDisabled = userRecord.isDisabled,
    )
  }

  fun toRecord() = UserRecord(
    userId = this.userId.toString(),
    loginId = this.loginId.toString(),
    password = this.password.toString(),
    roles = this.roles.joinToString(","),
    isUsingMfa = this.isUsingMfa,
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
    if (!this.isLock && this.failedAttempts >= MAX_LOGIN_ATTEMPTS) {
      this.isLock = true
      this.lockTime = LocalDateTime.now()
    }
  }

  fun unlockByTimeElapsed() {
    val isEnabledUnLocked =
      this.lockTime?.let {
        LocalDateTime.now().minusMinutes(AUTO_UNLOCK_DURATION_MIN.toLong()).isAfter(it)
      } ?: false

    if (this.isLock && isEnabledUnLocked) {
      this.isLock = false
      this.lockTime = null
    }
  }
}
