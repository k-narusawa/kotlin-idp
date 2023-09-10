package com.knarusawa.idp.domain.model.oneTimePassword

data class Code(
  val value: String
) {
  override fun toString(): String {
    return this.value
  }
}
