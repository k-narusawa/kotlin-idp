package com.knarusawa.demo.idp.idpdemo.application.service

import com.knarusawa.demo.idp.idpdemo.domain.model.error.AppException
import com.knarusawa.demo.idp.idpdemo.domain.model.error.ErrorCode
import com.knarusawa.demo.idp.idpdemo.domain.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository,
) : UserDetailsService {

  override fun loadUserByUsername(loginId: String): UserDetails {
    val user = userRepository.findByLoginId(loginId = loginId)
        ?: throw AppException(errorCode = ErrorCode.BAD_REQUEST, logMessage = "User Not Found.")
    return org.springframework.security.core.userdetails.User
        .withUsername(user.userId.toString())
        .password(user.password.value)
        .roles(user.roles.toString())
        .accountLocked(user.isLock)
        .disabled(user.isDisabled)
        .authorities(Collections.emptyList()).build()
  }
}