package com.knarusawa.idp.application.service.resetUserPassword

data class ResetUserPasswordInputData(
        val code: String,
        val password: String
)
