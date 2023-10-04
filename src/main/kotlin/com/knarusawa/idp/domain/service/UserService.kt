package com.knarusawa.idp.domain.service

import com.knarusawa.idp.domain.repository.UserRepository
import com.knarusawa.idp.domain.value.LoginId
import org.springframework.stereotype.Component

@Component
class UserService(
        private val userRepository: UserRepository
) {
    fun isExistsLoginId(loginId: LoginId): Boolean {
        val user = userRepository.findByLoginId(loginId = loginId.toString())
        return (user != null)
    }
}