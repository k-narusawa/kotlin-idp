package com.knarusawa.idp.infrastructure.dto

data class ChangeUserPasswordForm(
  val newPassword: String,
  val confirmPassword: String
)
