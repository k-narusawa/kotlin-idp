package com.knarusawa.idp.application.service.invalidateMfa

import com.knarusawa.idp.domain.repository.UserMfaRepository
import com.knarusawa.idp.domain.value.UserId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InvalidateMfaService(
        private val userMfaRepository: UserMfaRepository
) {
    @Transactional
    fun exec(input: InvalidateMfaInputData) {
        userMfaRepository.deleteByUserId(UserId(input.userId))
    }
}