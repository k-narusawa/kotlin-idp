package com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity

import java.time.LocalDateTime

data class UserWithRoleEntity(
  val userId: String,
  val loginId: String,
  val password: String,
  val lock: Boolean,
  val failedAttempts: Int?,
  val lockTime: LocalDateTime?,
  val isDisabled: Boolean,
  val roles: List<String>,
)