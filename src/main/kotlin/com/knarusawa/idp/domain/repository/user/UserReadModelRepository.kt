package com.knarusawa.idp.domain.repository.user

import com.knarusawa.idp.domain.model.user.UserReadModel

interface UserReadModelRepository {
  fun findByLoginId(loginId: String): UserReadModel?
}