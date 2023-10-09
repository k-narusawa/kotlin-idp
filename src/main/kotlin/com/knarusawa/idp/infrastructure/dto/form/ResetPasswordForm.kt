package com.knarusawa.idp.infrastructure.dto.form

data class ResetPasswordForm(
        val code: String,
        val password: String
)
