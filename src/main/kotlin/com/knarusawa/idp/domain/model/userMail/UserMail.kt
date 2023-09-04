package com.knarusawa.idp.domain.model.userMail

import com.knarusawa.idp.domain.model.user.UserId
import com.knarusawa.idp.infrastructure.adapter.db.record.UserMailRecord

class UserMail private constructor(
  val userId: UserId,
  var eMail: EMail,
  var isVerified: Boolean
) {
  companion object {
    fun of(userId: UserId, eMail: String) = UserMail(
      userId = userId,
      eMail = EMail(value = eMail),
      isVerified = false
    )

    fun from(record: UserMailRecord) = UserMail(
      userId = UserId(value = record.userId),
      eMail = EMail(value = record.email),
      isVerified = record.isVerified
    )
  }

  fun changeEMail(email: String) {
    this.eMail = EMail(value = email)
    this.isVerified = false
  }

  fun verify() {
    this.isVerified = true
  }
}