package com.knarusawa.idp.application.service.verifyOtp

import com.knarusawa.idp.domain.model.oneTimePassword.Code
import com.knarusawa.idp.domain.model.user.UserId
import com.knarusawa.idp.domain.repository.OnetimePasswordRepository
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class VerifyOtpService(
  private val onetimePasswordRepository: OnetimePasswordRepository
) {
  fun exec(input: VerifyOtpInput): Boolean {
    val oneTimePassword = runBlocking { onetimePasswordRepository.findByUserId(userId = UserId(input.userId)) }
      ?: return false

    runBlocking { onetimePasswordRepository.deleteByUserId(userId = UserId(input.userId)) }

    return oneTimePassword.verify(inputCode = Code(input.code))
  }
}