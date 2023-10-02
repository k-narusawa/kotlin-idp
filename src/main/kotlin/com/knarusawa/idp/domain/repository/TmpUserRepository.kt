package com.knarusawa.idp.domain.repository

import com.knarusawa.idp.domain.model.oneTimePassword.Code
import com.knarusawa.idp.domain.model.tmpUser.TmpUser
import com.knarusawa.idp.domain.model.user.LoginId


interface TmpUserRepository {
  fun save(tmpUser: TmpUser)
  fun findByCode(code: Code): TmpUser?
  fun deleteByLoginId(loginId: LoginId)
}