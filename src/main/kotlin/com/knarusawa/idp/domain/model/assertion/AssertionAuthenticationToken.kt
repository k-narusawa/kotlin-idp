package com.knarusawa.idp.domain.model.assertion

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User

class AssertionAuthenticationToken(
  private val principal: User,
  private val credentials: MfaCredentials,
  authorities: Collection<SimpleGrantedAuthority>,
) : AbstractAuthenticationToken(authorities) {
  override fun getPrincipal(): User {
    return principal
  }

  override fun getCredentials(): MfaCredentials {
    return credentials
  }
}