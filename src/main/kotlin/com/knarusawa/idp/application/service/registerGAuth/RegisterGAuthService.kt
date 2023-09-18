package com.knarusawa.idp.application.service.registerGAuth

import com.warrenstrange.googleauth.GoogleAuthenticator
import org.springframework.stereotype.Service

@Service
class RegisterGAuthService(
  private val gauth: GoogleAuthenticator
) {
  fun exec(input: RegisterGAuthInput): Boolean {
    val result = gauth.authorizeUser(
      input.userId,
      input.code.toInt()
    )

    return result
  }
}