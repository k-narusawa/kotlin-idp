package com.knarusawa.idp.application.service.registerMfa

data class RegisterMfaInputData(
        val userId: String,
        val code: String
)