package com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity

import java.time.LocalDateTime

data class UserEntity(
  val userId: String,
  val loginId: String,
  val password: String,
  val isLock: Boolean,
  val failedAttempts: Int?,
  val lockTime: LocalDateTime?,
  val isDisabled: Boolean,
  val createdAt: LocalDateTime?,
  val updatedAt: LocalDateTime?
)