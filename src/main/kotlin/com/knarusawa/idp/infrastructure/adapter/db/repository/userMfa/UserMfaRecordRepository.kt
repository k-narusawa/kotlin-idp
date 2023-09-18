package com.knarusawa.idp.infrastructure.adapter.db.repository.userMfa

import com.knarusawa.idp.infrastructure.adapter.db.record.UserMfaRecord
import org.springframework.data.repository.CrudRepository

interface UserMfaRecordRepository : CrudRepository<UserMfaRecord, String> {
  fun findByUserId(userId: String): UserMfaRecord?
}