package com.knarusawa.idp.application.service.registerGAuth

import com.warrenstrange.googleauth.GoogleAuthenticator
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class RegisterGAuthService(
  private val gauth: GoogleAuthenticator
) {
  @Transactional
  fun exec(input: RegisterGAuthInput): Boolean {
    return gauth.authorizeUser(
      input.userId,
      input.code.toInt()
    )
  }
}