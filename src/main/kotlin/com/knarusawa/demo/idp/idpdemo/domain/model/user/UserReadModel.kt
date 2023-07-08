package com.knarusawa.demo.idp.idpdemo.domain.model.user

import java.time.LocalDateTime

data class UserReadModel(
  val userId: UserId,
  val loginId: LoginId,
  val password: Password,
  val roles: List<Role>,
  val isLock: Boolean,
  val failedAttempts: Int?,
  val lockTime: LocalDateTime?,
  val isDisabled: Boolean,
  val createdAt: LocalDateTime,
  val updatedAt: LocalDateTime
)
