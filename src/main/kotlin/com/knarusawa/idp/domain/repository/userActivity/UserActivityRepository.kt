package com.knarusawa.idp.domain.repository.userActivity

import com.knarusawa.idp.infrastructure.db.record.UserActivityRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserActivityRepository : JpaRepository<UserActivityRecord, Int> {
  fun findByUserId(userId: String): List<UserActivityRecord>
}