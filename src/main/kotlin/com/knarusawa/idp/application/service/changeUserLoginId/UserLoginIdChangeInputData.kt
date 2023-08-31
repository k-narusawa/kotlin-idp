package com.knarusawa.idp.application.service.changeUserLoginId

data class UserLoginIdChangeInputData(
  val userId: String,
  val loginId: String,
)