package com.knarusawa.idp.domain.model.authentication

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.CredentialsContainer
import java.util.*


class MfaAuthentication(
  first: Authentication
) : AbstractAuthenticationToken(Collections.emptyList()) {
  private val first: Authentication

  init {
    this.first = first
  }

  override fun getPrincipal(): Any {
    return first.principal
  }

  override fun getCredentials(): Any {
    return first.credentials
  }

  override fun eraseCredentials() {
    if (first is CredentialsContainer) {
      (first as CredentialsContainer).eraseCredentials()
    }
  }

  override fun isAuthenticated(): Boolean {
    return false
  }

  fun getFirst(): Authentication {
    return first
  }
}