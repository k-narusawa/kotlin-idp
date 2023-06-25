package com.knarusawa.demo.idp.idpdemo.domain.model.user.command

class UserUpdateCommand(
  val userId: String,
  val loginId: String? = null,
  val password: String? = null,
  val isLock: Boolean? = null,
  val failedAttempt: Int? = null
)