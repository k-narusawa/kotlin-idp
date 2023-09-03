package com.knarusawa.idp.application.service

import com.knarusawa.idp.domain.repository.UserRepository
import java.util.*
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
  private val userRepository: UserRepository
) : UserDetailsService {

  override fun loadUserByUsername(loginId: String): UserDetails {
    val user = userRepository.findByLoginId(loginId = loginId)
      ?: throw UsernameNotFoundException("User Not Found.")

    // ロックされてから30分経過していたらアンロックする
    user.unlockByTimeElapsed()
    userRepository.save(user)

    return org.springframework.security.core.userdetails.User
      .withUsername(user.userId.toString())
      .password(user.password.value)
      .roles(*user.roles.map { it.name }.toTypedArray())
      .accountLocked(user.isLock)
      .disabled(user.isDisabled)
      .authorities(Collections.emptyList()).build()
  }
}