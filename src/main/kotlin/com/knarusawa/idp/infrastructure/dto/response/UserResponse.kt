package com.knarusawa.idp.infrastructure.dto.response

import java.time.LocalDateTime

data class UserResponse(
  val userId: String,
  val isLock: Boolean,
  val failedAttempts: Int?,
  val lockTime: LocalDateTime?,
  val isDisabled: Boolean,
  val roles: List<String>,
  val createdAd: LocalDateTime?,
  val updatedAt: LocalDateTime?
)
