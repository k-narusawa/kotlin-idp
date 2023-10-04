package com.knarusawa.idp.application.service.sendOtp

import com.knarusawa.idp.application.facade.MassageSenderFacade
import com.knarusawa.idp.application.service.query.UserDtoQueryService
import com.knarusawa.idp.domain.model.IdpAppException
import com.knarusawa.idp.domain.model.OneTimePassword
import com.knarusawa.idp.domain.repository.OnetimePasswordRepository
import com.knarusawa.idp.domain.value.ErrorCode
import com.knarusawa.idp.domain.value.MessageId
import com.knarusawa.idp.domain.value.UserId
import com.knarusawa.idp.infrastructure.middleware.logger
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class SendOtpService(
        private val userDtoQueryService: UserDtoQueryService,
        private val onetimePasswordRepository: OnetimePasswordRepository,
        private val messageSenderFacade: MassageSenderFacade
) {
    private val log = logger()

    @Transactional
    fun exec(input: SendOtpInputData) {
        val otp = OneTimePassword.of(userId = UserId(input.userId))
        log.info("ワンタイムパスワード: ${otp.code}")

        runBlocking { onetimePasswordRepository.save(otp) }

        val user = userDtoQueryService.findByUserId(userId = input.userId)
                ?: throw IdpAppException(
                        errorCode = ErrorCode.INTERNAL_SERVER_ERROR,
                        logMessage = "ユーザーが見つかりません"
                )

        messageSenderFacade.exec(
                toAddress = user.loginId,
                messageId = MessageId.MFA_MAIL_REGISTRATION,
                variables = listOf(Pair("#{otp}", otp.code.toString()))
        )
    }
}