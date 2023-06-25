package com.knarusawa.demo.idp.idpdemo.infrastructure.db.record

import com.knarusawa.demo.idp.idpdemo.domain.model.user.LoginId
import com.knarusawa.demo.idp.idpdemo.domain.model.user.Password
import com.knarusawa.demo.idp.idpdemo.domain.model.user.User
import java.time.LocalDateTime

data class UserRecord(
  val userId: String,
  val loginId: String,
  val password: String,
  val isLock: Boolean,
  val failedAttempts: Int?,
  val lockTime: LocalDateTime?,
  val isDisabled: Boolean,
  val createdAt: LocalDateTime?,
  val updatedAt: LocalDateTime?
) {
  fun toUser() = User(
    userId = this.userId,
    loginId = LoginId(value = this.loginId),
    password = Password(value = this.password),
    isLock = this.isLock,
    failedAttempts = this.failedAttempts,
    lockTime = this.lockTime,
    isDisabled = this.isDisabled,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
  )
}