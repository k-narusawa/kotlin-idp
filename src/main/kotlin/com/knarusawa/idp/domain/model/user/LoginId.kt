package com.knarusawa.idp.domain.model.user

data class LoginId(
  val value: String
) {
  init {
    if (!value.matches(Regex("^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\$")))
      throw IllegalArgumentException("メールアドレス形式でない")
  }

  override fun toString() = value
}
