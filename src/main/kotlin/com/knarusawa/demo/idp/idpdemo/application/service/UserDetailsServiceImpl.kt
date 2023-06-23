package com.knarusawa.demo.idp.idpdemo.application.service

import com.knarusawa.demo.idp.idpdemo.domain.model.user.User
import com.knarusawa.demo.idp.idpdemo.domain.repository.RoleRepository
import com.knarusawa.demo.idp.idpdemo.domain.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserDetailsServiceImpl(
  private val userRepository: UserRepository,
  private val roleRepository: RoleRepository,
) : UserDetailsService {

  override fun loadUserByUsername(loginId: String): UserDetails {
    val userEntity = userRepository.findByLoginId(loginId = loginId)
      ?: throw RuntimeException("user not found")
    val roleEntities = roleRepository.findByUserId(userId = userEntity.userId)
    val user = User.of(
      userEntity = userEntity,
      roleEntities = roleEntities
    )
    return org.springframework.security.core.userdetails.User
      .withUsername(user.userId)
      .password(user.password)
      .roles(*user.roles.toTypedArray().map { it.name }.toTypedArray())
      .accountLocked(user.isLock)
      .disabled(user.isDisabled)
      .authorities(Collections.emptyList()).build()
  }
}