package com.knarusawa.idp.application.service.changeUserPassword

data class ChangeUserPasswordInputData(
  val userId: String,
  val password: String,
)