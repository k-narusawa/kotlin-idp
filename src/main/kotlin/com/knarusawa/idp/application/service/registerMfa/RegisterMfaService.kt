package com.knarusawa.idp.application.service.registerMfa

import com.knarusawa.idp.domain.model.oneTimePassword.Code
import com.knarusawa.idp.domain.model.user.UserId
import com.knarusawa.idp.domain.model.userMfa.MfaType
import com.knarusawa.idp.domain.model.userMfa.UserMfa
import com.knarusawa.idp.domain.repository.OnetimePasswordRepository
import com.knarusawa.idp.domain.repository.UserMfaRepository
import jakarta.transaction.Transactional
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class RegisterMfaService(
  private val userMfaRepository: UserMfaRepository,
  private val onetimePasswordRepository: OnetimePasswordRepository
) {
  @Transactional
  fun exec(input: RegisterMfaInput): Boolean {
    val oneTimePassword =
      runBlocking { onetimePasswordRepository.findByUserId(userId = UserId(input.userId)) }
        ?: return false

    runBlocking { onetimePasswordRepository.deleteByUserId(userId = UserId(input.userId)) }

    val userMfa = UserMfa.of(
      userId = UserId(value = input.userId),
      type = MfaType.MAIL,
      secretKey = null,
      validationCode = null,
      scratchCodes = listOf(),
    )

    userMfaRepository.save(userMfa)

    return oneTimePassword.verify(inputCode = Code(input.code))
  }
}