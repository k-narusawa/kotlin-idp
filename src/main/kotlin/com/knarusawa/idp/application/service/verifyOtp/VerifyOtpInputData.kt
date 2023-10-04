package com.knarusawa.idp.application.service.verifyOtp

data class VerifyOtpInputData(
        val userId: String,
        val mfaType: String,
        val code: String
)