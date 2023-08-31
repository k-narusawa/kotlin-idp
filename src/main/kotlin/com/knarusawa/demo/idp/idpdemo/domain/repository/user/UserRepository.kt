package com.knarusawa.demo.idp.idpdemo.domain.repository.user

import com.knarusawa.demo.idp.idpdemo.infrastructure.db.record.UserRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRepository : JpaRepository<UserRecord, Int> {
  fun findByUserId(userId: String): UserRecord?
  fun findByLoginId(loginId: String): UserRecord?

}