package com.knarusawa.idp.domain.model.tmpUser

import com.knarusawa.idp.domain.model.oneTimePassword.Code
import com.knarusawa.idp.domain.model.user.LoginId
import java.io.Serializable

class TmpUser private constructor(
  val loginId: LoginId,
  val code: Code,
  val ttl: Long
) : Serializable {
  companion object {
    fun of(loginId: LoginId, ttl: Long?) = TmpUser(
      loginId = loginId,
      code = Code.generate(),
      ttl = ttl ?: 600L // デフォルトで600秒(10分)
    )
  }
}