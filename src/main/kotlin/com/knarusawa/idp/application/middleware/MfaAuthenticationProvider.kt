package com.knarusawa.idp.application.middleware

import com.knarusawa.idp.domain.model.assertion.AssertionAuthenticationToken
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class MfaAuthenticationProvider : AuthenticationProvider {
  override fun authenticate(authentication: Authentication?): Authentication {
    if (authentication !is AssertionAuthenticationToken) {
      throw BadCredentialsException("Invalid Assertion.")
    }
    return authentication
  }

  override fun supports(authentication: Class<*>?): Boolean {
    return authentication?.let {
      AssertionAuthenticationToken::class.java.isAssignableFrom(it)
    } ?: false
  }
}
