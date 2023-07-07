package com.knarusawa.demo.idp.idpdemo.application.service.user.register

data class UserRegisterInputData(
    val loginId: String,
    val password: String,
    val roles: List<String>
)