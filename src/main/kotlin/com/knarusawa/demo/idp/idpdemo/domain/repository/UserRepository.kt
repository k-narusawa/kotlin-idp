package com.knarusawa.demo.idp.idpdemo.domain.repository

import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.UserEntity

interface UserRepository {
  fun save(user: UserEntity): UserEntity
  fun findByLoginId(loginId: String): UserEntity?
  fun findByUserId(userId: String): UserEntity?
  fun findAll(): List<UserEntity>
}