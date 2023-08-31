package com.knarusawa.idp.application.service.changeUserPassword

data class UserPasswordChangeInputData(
  val userId: String,
  val password: String,
)