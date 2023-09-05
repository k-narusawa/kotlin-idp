package com.knarusawa.idp.domain.model

import com.knarusawa.idp.domain.model.user.User
import org.apereo.cas.client.validation.Assertion
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority

class AssertionAuthenticationToken(
  val principal: User,
  val credentials: MfaCredentials,
  authorities: Collection<SimpleGrantedAuthority>,
) : AbstractAuthenticationToken(authorities) {
  override fun getPrincipal(): Any {
    return principal
  }

  override fun getCredentials(): Any {
    return credentials
  }

  class MfaCredentials(
    val sessionId: String,
    val assertion: Assertion,
  )
}