package com.knarusawa.idp.application.service.verifyOtp

data class VerifyOtpInput(
  val userId: String,
  val mfaType: String,
  val code: String
)