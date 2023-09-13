package com.knarusawa.idp.application.service

import com.knarusawa.idp.domain.model.user.UserId
import com.knarusawa.idp.domain.repository.OnetimePasswordRepository
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CheckMfaService(
  private val oneTimePasswordRepository: OnetimePasswordRepository
) {
  fun exec(userId: String, otp: String): Boolean {
    val oneTimePassword =
      runBlocking { oneTimePasswordRepository.findByUserId(userId = UserId(userId)) }
        ?: return false

    if (oneTimePassword.expired.isBefore(LocalDateTime.now()))
      return false

    if (oneTimePassword.code.toString() != otp)
      return false

    runBlocking { oneTimePasswordRepository.deleteByUserId(userId = UserId(userId)) }
    
    return true
  }
}