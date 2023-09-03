package com.knarusawa.idp.infrastructure.adapter.db.repository.userMail

import com.knarusawa.idp.infrastructure.adapter.db.record.UserMailRecord
import org.springframework.data.jpa.repository.JpaRepository

interface UserMailRecordRepository : JpaRepository<UserMailRecord, Int> {
  fun findByUserId(userId: String): UserMailRecord?

  fun findByEmail(eMail: String): UserMailRecord?
}