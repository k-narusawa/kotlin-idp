package com.knarusawa.demo.idp.idpdemo.application.service.user.register

data class UserRegisterCommand(
    val loginId: String,
    val password: String,
    val roles: List<String>
)