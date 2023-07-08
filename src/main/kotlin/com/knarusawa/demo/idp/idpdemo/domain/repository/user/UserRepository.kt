package com.knarusawa.demo.idp.idpdemo.domain.repository.user

import com.knarusawa.demo.idp.idpdemo.domain.model.user.User

interface UserRepository {
  fun save(user: User): User
  fun update(user: User): User
  fun findByUserId(userId: String): User?
}