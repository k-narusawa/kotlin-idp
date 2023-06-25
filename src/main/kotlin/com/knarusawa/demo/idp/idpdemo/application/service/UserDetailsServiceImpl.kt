package com.knarusawa.demo.idp.idpdemo.application.service

import com.knarusawa.demo.idp.idpdemo.domain.model.error.AppException
import com.knarusawa.demo.idp.idpdemo.domain.model.error.ErrorCode
import com.knarusawa.demo.idp.idpdemo.domain.repository.UserRepository
import com.knarusawa.demo.idp.idpdemo.domain.repository.UserRoleRepository
import java.util.*
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
  private val userRepository: UserRepository,
  private val userRoleRepository: UserRoleRepository,
) : UserDetailsService {

  override fun loadUserByUsername(loginId: String): UserDetails {
    val user = userRepository.findByLoginId(loginId = loginId)
      ?: throw AppException(errorCode = ErrorCode.BAD_REQUEST, logMessage = "User Not Found.")
    val userRole = userRoleRepository.findByUserId(userId = user.userId)
    return org.springframework.security.core.userdetails.User
      .withUsername(user.userId)
      .password(user.password.value)
      .roles(*userRole.map { it.role.name }.toTypedArray())
      .accountLocked(user.isLock)
      .disabled(user.isDisabled)
      .authorities(Collections.emptyList()).build()
  }
}