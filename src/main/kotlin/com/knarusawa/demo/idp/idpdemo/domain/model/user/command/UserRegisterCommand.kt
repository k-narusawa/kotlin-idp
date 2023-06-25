package com.knarusawa.demo.idp.idpdemo.domain.model.user.command

class UserRegisterCommand(
  val loginId: String,
  val password: String,
  val roles: List<String>
)