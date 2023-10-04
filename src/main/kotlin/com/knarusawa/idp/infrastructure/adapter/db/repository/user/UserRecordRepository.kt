package com.knarusawa.idp.infrastructure.adapter.db.repository.user

import com.knarusawa.idp.infrastructure.adapter.db.record.UserRecord
import org.springframework.data.jpa.repository.JpaRepository

interface UserRecordRepository : JpaRepository<UserRecord, Int> {
    fun findByUserId(userId: String): UserRecord?
    fun findByLoginId(loginId: String): UserRecord?
    fun deleteByUserId(userId: String)
}