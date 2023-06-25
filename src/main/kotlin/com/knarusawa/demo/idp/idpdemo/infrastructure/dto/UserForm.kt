package com.knarusawa.demo.idp.idpdemo.infrastructure.dto

data class UserForm(
  val loginId: String,
  val password: String,
  val roles: List<String>
)
