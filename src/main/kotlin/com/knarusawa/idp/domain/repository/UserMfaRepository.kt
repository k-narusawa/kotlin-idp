package com.knarusawa.idp.domain.repository

import com.knarusawa.idp.domain.model.UserMfa
import com.knarusawa.idp.domain.value.UserId

interface UserMfaRepository {
    fun findByUserId(userId: UserId): UserMfa?
    fun save(userMfa: UserMfa)
    fun deleteByUserId(userId: UserId)
}