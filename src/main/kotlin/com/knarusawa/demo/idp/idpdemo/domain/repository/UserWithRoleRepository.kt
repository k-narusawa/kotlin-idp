package com.knarusawa.demo.idp.idpdemo.domain.repository

import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.UserWithRoleEntity

interface UserWithRoleRepository {
  fun findByLoginId(loginId: String): UserWithRoleEntity?
}