package com.knarusawa.idp.application.service.generateGAuth

import com.warrenstrange.googleauth.GoogleAuthenticator
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator
import org.springframework.stereotype.Service


@Service
class GenerateGAuthService(
  private val gauth: GoogleAuthenticator
) {
  companion object {
    private const val ISSUER = "kotlin-idp"
  }

  fun exec(input: GenerateGAuthInput): String {
    val key = gauth.createCredentials()

    return GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL(ISSUER, input.userId, key)
  }
}