package com.knarusawa.idp.infrastructure.adapter.db.repository.userMfa

import com.knarusawa.idp.domain.model.UserMfa
import com.knarusawa.idp.domain.repository.UserMfaRepository
import com.knarusawa.idp.domain.value.UserId
import com.knarusawa.idp.infrastructure.adapter.db.record.UserMfaRecord
import org.springframework.stereotype.Repository

@Repository
class UserMfaRepositoryImpl(
        private val userMfaRecordRepository: UserMfaRecordRepository
) : UserMfaRepository {
    override fun findByUserId(userId: UserId): UserMfa? {
        val record = userMfaRecordRepository.findByUserId(userId = userId.toString())
        return record?.to()
    }

    override fun save(userMfa: UserMfa) {
        userMfaRecordRepository.save(UserMfaRecord.from(userMfa = userMfa))
    }

    override fun deleteByUserId(userId: UserId) {
        userMfaRecordRepository.deleteByUserId(userId = userId.toString())
    }
}