package com.knarusawa.idp.domain.model.authority

import org.springframework.security.core.GrantedAuthority

class IdpGrantedAuthority private constructor(
  private val authorityRole: AuthorityRole
) : GrantedAuthority {

  companion object {
    fun useMfaApp(): IdpGrantedAuthority {
      return IdpGrantedAuthority(authorityRole = AuthorityRole.MFA_APP)
    }

    fun useMfaMail(): IdpGrantedAuthority {
      return IdpGrantedAuthority(authorityRole = AuthorityRole.MFA_MAIL)
    }

    fun useMfaSms(): IdpGrantedAuthority {
      return IdpGrantedAuthority(authorityRole = AuthorityRole.MFA_SMS)
    }

    fun usePassword(): IdpGrantedAuthority {
      return IdpGrantedAuthority(authorityRole = AuthorityRole.PASSWORD)
    }
  }

  override fun getAuthority(): String {
    return this.authorityRole.toString()
  }
}