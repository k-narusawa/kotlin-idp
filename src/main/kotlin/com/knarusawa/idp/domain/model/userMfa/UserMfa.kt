package com.knarusawa.idp.domain.model.userMfa

import com.knarusawa.idp.domain.model.user.UserId

class UserMfa private constructor(
  val userId: UserId,
  val type: MfaType,
  val secretKey: String?,
  val validationCode: Int?,
  val scratchCodes: List<Int>
) {
  companion object {
    fun of(
      userId: UserId,
      type: MfaType,
      secretKey: String?,
      validationCode: Int?,
      scratchCodes: List<Int>?
    ) = UserMfa(
      userId = userId,
      type = type,
      secretKey = secretKey,
      validationCode = validationCode,
      scratchCodes = scratchCodes ?: listOf(),
    )
  }
}