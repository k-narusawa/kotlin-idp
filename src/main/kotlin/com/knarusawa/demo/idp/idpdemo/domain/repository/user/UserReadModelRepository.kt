package com.knarusawa.demo.idp.idpdemo.domain.repository.user

import com.knarusawa.demo.idp.idpdemo.domain.model.user.UserReadModel

interface UserReadModelRepository {
  fun findByLoginId(loginId: String): UserReadModel?
  fun findByUserId(userId: String): UserReadModel?
  fun findAll(): List<UserReadModel>
}