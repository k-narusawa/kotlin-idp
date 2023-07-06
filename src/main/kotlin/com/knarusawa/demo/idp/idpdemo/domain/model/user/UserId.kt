package com.knarusawa.demo.idp.idpdemo.domain.model.user

data class UserId(
    val value: String
) {
  companion object {
    fun generate(): UserId {
      return UserId(java.util.UUID.randomUUID().toString())
    }
  }

  override fun toString() = value
}
