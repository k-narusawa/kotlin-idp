package com.knarusawa.demo.idp.idpdemo.domain.model.user

import com.knarusawa.demo.idp.idpdemo.configuration.SecurityConfig
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.UserEntity
import java.time.LocalDateTime

class User private constructor(
  userId: UserId,
  loginId: LoginId,
  password: Password,
  roles: List<Role>,
  isLock: Boolean,
  failedAttempts: Int?,
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

    fun from(userEntity: UserEntity) = User(
      userId = UserId(value = userEntity.userId),
      loginId = LoginId(value = userEntity.loginId),
      password = Password(value = userEntity.password),
      roles = userEntity.roles.split(",").map { Role.fromString(it) },
      isLock = userEntity.isLock,
      failedAttempts = userEntity.failedAttempts,
      lockTime = userEntity.lockTime,
      isDisabled = userEntity.isDisabled,
    )
  }

  fun toEntity() = UserEntity(
    userId = this.userId.toString(),
    loginId = this.loginId.toString(),
    password = this.password.toString(),
    roles = this.roles.joinToString(","),
    isLock = this.isLock,
    failedAttempts = this.failedAttempts,
    lockTime = this.lockTime,
    isDisabled = this.isDisabled,
  )

  fun updateLoginId(loginId: String) {
    this.loginId = LoginId(value = loginId)
  }

  fun updatePassword(password: String) {
    this.password = Password(value = SecurityConfig().passwordEncoder().encode(password))
  }
}
