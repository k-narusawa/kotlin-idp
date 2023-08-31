package com.knarusawa.idp.domain.repository.user

import com.knarusawa.idp.infrastructure.db.record.UserRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRepository : JpaRepository<UserRecord, Int> {
  fun findByUserId(userId: String): UserRecord?
  fun findByLoginId(loginId: String): UserRecord?

}