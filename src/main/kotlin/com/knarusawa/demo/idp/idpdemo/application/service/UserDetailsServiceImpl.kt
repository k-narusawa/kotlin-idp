package com.knarusawa.demo.idp.idpdemo.application.service

import com.knarusawa.demo.idp.idpdemo.domain.model.error.AppException
import com.knarusawa.demo.idp.idpdemo.domain.model.error.ErrorCode
import com.knarusawa.demo.idp.idpdemo.domain.model.user.User
import com.knarusawa.demo.idp.idpdemo.domain.repository.RoleRepository
import com.knarusawa.demo.idp.idpdemo.domain.repository.UserRepository
import java.util.*
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
  private val userRepository: UserRepository,
  private val roleRepository: RoleRepository,
) : UserDetailsService {

  override fun loadUserByUsername(loginId: String): UserDetails {
    val userEntity = userRepository.findByLoginId(loginId = loginId)
      ?: throw AppException(errorCode = ErrorCode.BAD_REQUEST, errorMessage = "User Not Found.")
    val roleEntities = roleRepository.findByUserId(userId = userEntity.userId)
    val user = User.of(
      userRecord = userEntity,
      roleEntities = roleEntities
    )
    return org.springframework.security.core.userdetails.User
      .withUsername(user.userId)
      .password(user.password.value)
      .roles(*user.roles.toTypedArray().map { it.name }.toTypedArray())
      .accountLocked(user.isLock)
      .disabled(user.isDisabled)
      .authorities(Collections.emptyList()).build()
  }
}