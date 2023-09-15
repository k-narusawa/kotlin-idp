package com.knarusawa.idp.domain.model.userMfa

import com.knarusawa.idp.domain.model.user.UserId

class UserMfa private constructor(
  val userId: UserId,
  val type: MfaType,
  val target: String,
) {
  companion object {
    fun of(userId: UserId, type: MfaType, target: String) = UserMfa(
      userId = userId,
      type = type,
      target = target
    )
  }
}