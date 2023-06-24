package com.knarusawa.demo.idp.idpdemo.domain.repository

import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.UserRecord

interface UserRepository {
  fun save(user: UserRecord): UserRecord
  fun findByLoginId(loginId: String): UserRecord?
  fun findByUserId(userId: String): UserRecord?
  fun findAll(): List<UserRecord>
}