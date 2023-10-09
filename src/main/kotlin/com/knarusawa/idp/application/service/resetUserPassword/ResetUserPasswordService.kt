package com.knarusawa.idp.application.service.resetUserPassword

import com.knarusawa.idp.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class ResetUserPasswordService(
        private val userRepository: UserRepository
) {
    fun exec() {

    }
}