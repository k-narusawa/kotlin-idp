package com.knarusawa.idp.domain.model.user

data class Password(
  val value: String?
) {
  override fun toString() = value ?: ""
}
