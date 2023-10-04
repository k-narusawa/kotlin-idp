package com.knarusawa.idp.domain.repository

import com.knarusawa.idp.domain.model.TmpUser
import com.knarusawa.idp.domain.value.Code
import com.knarusawa.idp.domain.value.LoginId


interface TmpUserRepository {
    fun save(tmpUser: TmpUser)
    fun findByCode(code: Code): TmpUser?
    fun deleteByLoginId(loginId: LoginId)
}