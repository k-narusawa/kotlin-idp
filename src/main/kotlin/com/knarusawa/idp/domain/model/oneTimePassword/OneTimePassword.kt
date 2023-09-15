package com.knarusawa.idp.domain.model.oneTimePassword

import com.knarusawa.idp.domain.model.user.UserId
import java.time.LocalDateTime


class OneTimePassword private constructor(
  val userId: UserId,
  val code: Code,
  val expired: LocalDateTime
) {
  companion object {
    private const val EXPIRED_MINUTES = 30
    fun of(userId: UserId) = OneTimePassword(
      userId = userId,
      code = Code.generate(),
      expired = LocalDateTime.now().plusMinutes(EXPIRED_MINUTES.toLong())
    )

    fun of(userId: String, code: String, expired: String) = OneTimePassword(
      userId = UserId(userId),
      code = Code(value = code),
      expired = LocalDateTime.parse(expired)
    )
  }

  fun verify(inputCode: Code): Boolean {
    if (this.expired.isBefore(LocalDateTime.now()))
      return false

    if (this.code != inputCode)
      return false

    return true
  }
}