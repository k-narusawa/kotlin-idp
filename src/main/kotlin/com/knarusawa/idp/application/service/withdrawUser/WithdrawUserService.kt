package com.knarusawa.idp.application.service.withdrawUser

import com.knarusawa.idp.domain.model.error.ErrorCode
import com.knarusawa.idp.domain.model.error.IdpAppException
import com.knarusawa.idp.domain.model.user.UserId
import com.knarusawa.idp.domain.model.userWithdraw.UserWithdraw
import com.knarusawa.idp.domain.repository.UserRepository
import com.knarusawa.idp.domain.repository.UserWithdrawRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class WithdrawUserService(
        private val userRepository: UserRepository,
        private val userWithdrawRepository: UserWithdrawRepository
) {
    @Transactional
    fun exec(input: WithdrawUserInputData) {
        val user = userRepository.findByUserId(userId = input.userId)
                ?: throw IdpAppException(
                        errorCode = ErrorCode.USER_NOT_FOUND,
                        logMessage = "退会対象のユーザーが見つかりません"
                )

        userRepository.deleteByUserId(input.userId)

        val userWithdraw = UserWithdraw.of(
                userId = UserId(value = input.userId),
                loginId = user.loginId
        )

        userWithdrawRepository.save(userWithdraw)
    }
}