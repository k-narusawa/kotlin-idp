package com.knarusawa.idp.application.service.resetUserPassword

import com.knarusawa.idp.domain.model.IdpAppException
import com.knarusawa.idp.domain.repository.PasswordResetDataRepository
import com.knarusawa.idp.domain.repository.UserRepository
import com.knarusawa.idp.domain.value.ErrorCode
import com.knarusawa.idp.domain.value.PasswordResetKey
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ResetUserPasswordService(
        private val userRepository: UserRepository,
        private val passwordResetDataRepository: PasswordResetDataRepository
) {
    @Transactional
    fun exec(input: ResetUserPasswordInputData) {
        val data = passwordResetDataRepository.findByKey(PasswordResetKey.fromCode(input.code).toString())
                ?: throw IdpAppException(errorCode = ErrorCode.BAD_REQUEST, logMessage = "認証コードが間違っています")

        val user = userRepository.findByLoginId(loginId = data.loginId)
                ?: throw IdpAppException(errorCode = ErrorCode.INTERNAL_SERVER_ERROR, logMessage = "ユーザーが見つかりませんでした")

        user.changePassword(input.password)
        userRepository.save(user)
    }
}