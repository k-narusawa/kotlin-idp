package com.knarusawa.idp.application.service.verifyOtp

import com.knarusawa.idp.domain.repository.OnetimePasswordRepository
import com.knarusawa.idp.domain.value.Code
import com.knarusawa.idp.domain.value.MfaType
import com.knarusawa.idp.domain.value.UserId
import com.warrenstrange.googleauth.GoogleAuthenticator
import jakarta.transaction.Transactional
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class VerifyOtpService(
        private val gauth: GoogleAuthenticator,
        private val onetimePasswordRepository: OnetimePasswordRepository
) {
    @Transactional
    fun exec(input: VerifyOtpInputData): Boolean {
        val mfaType = MfaType.from(input.mfaType)

        return when (mfaType) {
            MfaType.APP -> appAuth(userId = input.userId, code = input.code)
            MfaType.MAIL -> mailAuth(userId = input.userId, code = input.code)
            MfaType.SMS -> mailAuth(userId = input.userId, code = input.code)
        }
    }

    private fun appAuth(userId: String, code: String): Boolean {
        return gauth.authorizeUser(userId, code.toInt())
    }

    private fun mailAuth(userId: String, code: String): Boolean {
        val oneTimePassword =
                runBlocking { onetimePasswordRepository.findByUserId(userId = UserId(userId)) }
                        ?: return false

        runBlocking { onetimePasswordRepository.deleteByUserId(userId = UserId(userId)) }

        return oneTimePassword.verify(inputCode = Code(code))
    }
}