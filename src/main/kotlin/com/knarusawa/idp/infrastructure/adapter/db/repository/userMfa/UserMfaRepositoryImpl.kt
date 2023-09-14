package com.knarusawa.idp.infrastructure.adapter.db.repository.userMfa

import com.knarusawa.idp.domain.model.user.UserId
import com.knarusawa.idp.domain.model.userMfa.UserMfa
import com.knarusawa.idp.domain.repository.UserMfaRepository
import com.knarusawa.idp.infrastructure.adapter.db.record.UserMfaRecord

class UserMfaRepositoryImpl(
  private val userMfaRecordRepository: UserMfaRecordRepository
) : UserMfaRepository {
  override fun findByUserId(userId: UserId): UserMfa {
    val record = userMfaRecordRepository.findByUserId(userId = userId.toString())
    return record.to()
  }

  override fun save(userMfa: UserMfa) {
    userMfaRecordRepository.save(UserMfaRecord.from(userMfa = userMfa))
  }
}