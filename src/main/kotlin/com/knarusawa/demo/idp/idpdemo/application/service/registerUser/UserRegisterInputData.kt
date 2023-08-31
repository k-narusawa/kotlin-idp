package com.knarusawa.demo.idp.idpdemo.application.service.registerUser

data class UserRegisterInputData(
  val loginId: String,
  val password: String,
  val roles: List<String>
)