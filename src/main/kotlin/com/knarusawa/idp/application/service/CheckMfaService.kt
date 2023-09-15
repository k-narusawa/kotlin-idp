package com.knarusawa.idp.application.service

import com.knarusawa.idp.domain.model.oneTimePassword.Code
import com.knarusawa.idp.domain.model.user.UserId
import com.knarusawa.idp.domain.repository.OnetimePasswordRepository
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class CheckMfaService(
  private val oneTimePasswordRepository: OnetimePasswordRepository
) {
  fun exec(userId: String, otp: String): Boolean {
    val oneTimePassword =
      runBlocking { oneTimePasswordRepository.findByUserId(userId = UserId(userId)) }
        ?: return false

    if (!oneTimePassword.verify(inputCode = Code(otp)))
      return false

    runBlocking { oneTimePasswordRepository.deleteByUserId(userId = UserId(userId)) }

    return true
  }
}