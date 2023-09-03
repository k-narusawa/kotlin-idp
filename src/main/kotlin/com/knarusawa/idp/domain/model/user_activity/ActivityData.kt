package com.knarusawa.idp.domain.model.user_activity

data class ActivityData(
  val value: String?
) {
  override fun toString(): String {
    return this.value ?: "{}"
  }
}
