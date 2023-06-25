package com.knarusawa.demo.idp.idpdemo.domain.repository

import com.knarusawa.demo.idp.idpdemo.domain.model.user.User

interface UserRepository {
  fun save(user: User): User
  fun findByLoginId(loginId: String): User?
  fun findByUserId(userId: String): User?
  fun findAll(): List<User>
}