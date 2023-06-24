package com.knarusawa.demo.idp.idpdemo.domain.model.user

data class Password(
  val value: String
) {
  override fun toString(): String {
    return value
  }
}
