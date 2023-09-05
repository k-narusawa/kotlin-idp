package com.knarusawa.idp.application.middleware

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class MfaAuthenticationProvider : AuthenticationProvider {
  override fun authenticate(authentication: Authentication?): Authentication {
    return authentication!!
  }

  override fun supports(authentication: Class<*>?): Boolean {
    return true
  }
}