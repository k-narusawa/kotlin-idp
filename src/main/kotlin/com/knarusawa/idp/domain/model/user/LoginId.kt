package com.knarusawa.idp.domain.model.user

data class LoginId(
  val value: String
) {
  override fun toString() = value
}
