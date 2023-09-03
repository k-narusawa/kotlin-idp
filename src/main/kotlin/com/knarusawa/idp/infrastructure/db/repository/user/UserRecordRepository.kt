package com.knarusawa.idp.infrastructure.db.repository.user

import com.knarusawa.idp.infrastructure.db.record.UserRecord
import org.springframework.data.jpa.repository.JpaRepository

interface UserRecordRepository : JpaRepository<UserRecord, Int> {
  fun findByUserId(userId: String): UserRecord?
  fun findByLoginId(loginId: String): UserRecord?
}