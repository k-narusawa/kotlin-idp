package com.knarusawa.idp.application.service.requestResetUserPassword

import com.knarusawa.idp.application.facade.MassageSenderFacade
import com.knarusawa.idp.domain.model.PasswordResetData
import com.knarusawa.idp.domain.repository.PasswordResetDataRepository
import com.knarusawa.idp.domain.repository.UserRepository
import com.knarusawa.idp.domain.value.LoginId
import com.knarusawa.idp.domain.value.MessageId
import org.springframework.stereotype.Service

@Service
class RequestResetUserPasswordService(
        private val userRepository: UserRepository,
        private val messageSenderFacade: MassageSenderFacade,
        private val passwordResetDataRepository: PasswordResetDataRepository,
) {
    fun exec(input: RequestResetUserPasswordSInputData) {
        val user = userRepository.findByLoginId(loginId = input.loginId)
                ?: return

        val data = PasswordResetData.of(LoginId(input.loginId))

        passwordResetDataRepository.save(data)
        
        messageSenderFacade.exec(
                toAddress = user.loginId.toString(),
                messageId = MessageId.USER_PASSWORD_RESET,
                variables = listOf(Pair("#{otp}", data.key.getCode()))
        )
    }
}