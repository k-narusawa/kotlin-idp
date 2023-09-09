package com.knarusawa.idp.infrastructure.dto.form

data class UserForm(
  val loginId: String,
  val password: String,
  val eMail: String
)
