package com.knarusawa.idp.application.service.sendOtp

import com.knarusawa.idp.domain.model.oneTimePassword.OneTimePassword
import com.knarusawa.idp.domain.model.user.UserId
import com.knarusawa.idp.domain.repository.OnetimePasswordRepository
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class SendOtpService(
  private val onetimePasswordRepository: OnetimePasswordRepository,
) {
  companion object {
    val logger = LoggerFactory.getLogger(SendOtpService::class.java)
  }

  @Transactional
  fun exec(input: SendOtpInput) {
    val otp = OneTimePassword.of(userId = UserId(input.userId))
    logger.info("ワンタイムパスワード: ${otp.code}")
    runBlocking { onetimePasswordRepository.save(otp) }
  }
}