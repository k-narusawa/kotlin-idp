package com.knarusawa.demo.idp.idpdemo.domain.repository

import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.RoleRecord

interface RoleRepository {
  fun save(role: RoleRecord): RoleRecord
  fun findByUserId(userId: String): List<RoleRecord>
}