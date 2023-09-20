package com.knarusawa.idp.domain.repository

import com.knarusawa.idp.domain.model.user.UserId
import com.knarusawa.idp.domain.model.userMfa.UserMfa

interface UserMfaRepository {
  fun findByUserId(userId: UserId): UserMfa?
  fun save(userMfa: UserMfa)
  fun deleteByUserId(userId: UserId)
}