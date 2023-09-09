package com.knarusawa.idp.domain.model.authority

enum class AuthorityRole {
  PASSWORD,
  MFA_APP,
  MFA_MAIL,
  MFA_SMS,
  ;

  override fun toString(): String {
    return this.name
  }
}