package com.knarusawa.idp.application.service.requestChangeUserLoginId

import com.knarusawa.idp.application.facade.MassageSenderFacade
import com.knarusawa.idp.domain.model.LoginIdUpdateDate
import com.knarusawa.idp.domain.repository.LoginIdUpdateDateRepository
import com.knarusawa.idp.domain.repository.UserRepository
import com.knarusawa.idp.domain.service.UserService
import com.knarusawa.idp.domain.value.LoginId
import com.knarusawa.idp.domain.value.MessageId
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class RequestChangeUserLoginIdService(
        private val userService: UserService,
        private val userRepository: UserRepository,
        private val loginIdUpdateDateRepository: LoginIdUpdateDateRepository,
        private val messageSenderFacade: MassageSenderFacade,
) {
    @Transactional
    fun exec(input: RequestChangeUserLoginIdInputData) {
        val user = userRepository.findByUserId(userId = input.userId)

        if (user == null) {
            sendFailedMessage(input.loginId)
            return
        }

        if (userService.isExistsLoginId(loginId = LoginId(input.loginId))) {
            sendFailedMessage(input.loginId)
            return
        }

        val data = LoginIdUpdateDate.of(
                userId = user.userId,
                loginId = LoginId(input.loginId)
        )

        loginIdUpdateDateRepository.save(data)

        messageSenderFacade.exec(
                toAddress = input.loginId,
                messageId = MessageId.USER_LOGIN_ID_UPDATE,
                variables = listOf(Pair("#{otp}", data.key.getCode()))
        )
    }

    private fun sendFailedMessage(to: String) {
        messageSenderFacade.exec(
                toAddress = to,
                messageId = MessageId.USER_LOGIN_ID_UPDATE_FAILED,
                variables = listOf()
        )
    }

}