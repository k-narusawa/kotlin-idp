package com.knarusawa.demo.idp.idpdemo.application.service

import com.knarusawa.demo.idp.idpdemo.domain.model.userRole.UserRole
import com.knarusawa.demo.idp.idpdemo.domain.repository.UserRoleRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserRoleService(
  private val userRoleRepository: UserRoleRepository
) {
  fun getUserRole(userId: String): List<UserRole> {
    return userRoleRepository.findByUserId(userId = userId)
  }
}