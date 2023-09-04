package com.knarusawa.idp.domain.model.userMail

data class EMail(
  val value: String
) {
  init {
    if (
      value.isEmpty() ||
      !value.matches(Regex("^[a-zA-Z0-9]+@[a-zA-Z0-9]+(.[a-zA-Z]{2,})$"))
    ) {
      throw IllegalArgumentException("$value is not a valid email address")
    }
  }

  override fun toString(): String {
    return this.value
  }
}
