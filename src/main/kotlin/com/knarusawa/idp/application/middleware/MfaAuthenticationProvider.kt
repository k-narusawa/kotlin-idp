package com.knarusawa.idp.application.middleware

import com.knarusawa.idp.domain.model.assertion.AssertionAuthenticationToken
import com.knarusawa.idp.domain.model.user.UserId
import com.knarusawa.idp.domain.repository.OnetimePasswordRepository
import java.time.LocalDateTime
import kotlinx.coroutines.runBlocking
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class MfaAuthenticationProvider(
  private val oneTimePasswordRepository: OnetimePasswordRepository
) : AuthenticationProvider {
  override fun authenticate(authentication: Authentication?): Authentication {
    if (authentication is AssertionAuthenticationToken) {
      verifyOtp(
        userId = authentication.principal.username,
        otp = authentication.credentials.code
      )
    } else {
      throw BadCredentialsException("アサーションが不正です")
    }


    return authentication
  }

  override fun supports(authentication: Class<*>?): Boolean {
    return authentication?.let {
      AssertionAuthenticationToken::class.java.isAssignableFrom(it)
    } ?: false
  }

  /**
   * OTPの検証を行う
   *
   * @param otp ワンタイムパスワード
   */
  private fun verifyOtp(userId: String, otp: String) {
    val oneTimePassword =
      runBlocking { oneTimePasswordRepository.findByUserId(userId = UserId(userId)) }
        ?: throw AuthenticationServiceException("ワンタイムパスワードの検証に失敗しました")

    if (oneTimePassword.expired.isBefore(LocalDateTime.now()))
      throw AuthenticationServiceException("ワンタイムパスワードの検証に失敗しました")

    if (oneTimePassword.code.toString() != otp)
      throw AuthenticationServiceException("ワンタイムパスワードの検証に失敗しました")

    runBlocking { oneTimePasswordRepository.deleteByUserId(userId = UserId(userId)) }
  }
}
