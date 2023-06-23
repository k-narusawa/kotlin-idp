package com.knarusawa.demo.idp.idpdemo.domain.repository

import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.RoleEntity

interface RoleRepository {
  fun save(role: RoleEntity): RoleEntity
  fun findByUserId(userId: String): List<RoleEntity>
}