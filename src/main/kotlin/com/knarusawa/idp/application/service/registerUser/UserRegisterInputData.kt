package com.knarusawa.idp.application.service.registerUser

data class UserRegisterInputData(
  val loginId: String,
  val password: String,
  val eMail: String,
)