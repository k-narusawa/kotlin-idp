package com.knarusawa.idp.application.service.registerMfa

data class RegisterMfaInput(
  val userId: String,
  val code: String
)