package com.knarusawa.idp.infrastructure.dto.form

data class ChangeUserPasswordForm(
  val newPassword: String,
  val confirmPassword: String
)
