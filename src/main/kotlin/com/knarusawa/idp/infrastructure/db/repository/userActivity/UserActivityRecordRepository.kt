package com.knarusawa.idp.infrastructure.db.repository.userActivity

import com.knarusawa.idp.infrastructure.db.record.UserActivityRecord
import org.springframework.data.jpa.repository.JpaRepository

interface UserActivityRecordRepository : JpaRepository<UserActivityRecord, Int> {
  fun findByUserId(userId: String): List<UserActivityRecord>
}