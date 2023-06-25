package com.knarusawa.demo.idp.idpdemo.domain.repository

import com.knarusawa.demo.idp.idpdemo.domain.model.userRole.UserRole

interface UserRoleRepository {
  fun save(role: UserRole): UserRole
  fun findByUserId(userId: String): List<UserRole>
}