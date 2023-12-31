package com.knarusawa.idp.application.service.registerTmpUser

import com.knarusawa.idp.application.facade.MassageSenderFacade
import com.knarusawa.idp.domain.model.TmpUser
import com.knarusawa.idp.domain.repository.TmpUserRepository
import com.knarusawa.idp.domain.service.UserService
import com.knarusawa.idp.domain.value.LoginId
import com.knarusawa.idp.domain.value.MessageId
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterTmpUserService(
        private val userService: UserService,
        private val tmpUserRepository: TmpUserRepository,
        private val messageSenderFacade: MassageSenderFacade
) {
    @Transactional
    fun exec(input: RegisterTmpUserInputData) {
        if (userService.isExistsLoginId(loginId = LoginId(input.loginId)))
            messageSenderFacade.exec(
                    toAddress = input.loginId,
                    messageId = MessageId.TMP_USER_CONFIRM_FAILED,
                    variables = listOf()
            )

        val tmpUser = TmpUser.of(
                loginId = LoginId(value = input.loginId),
                ttl = null
        )

        tmpUserRepository.save(tmpUser)

        messageSenderFacade.exec(
                toAddress = input.loginId,
                messageId = MessageId.TMP_USER_CONFIRM,
                variables = listOf(Pair("otp", tmpUser.code))
        )
    }
}