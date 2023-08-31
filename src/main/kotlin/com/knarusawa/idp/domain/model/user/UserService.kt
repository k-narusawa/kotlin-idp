package com.knarusawa.idp.domain.model.user

import com.knarusawa.idp.domain.repository.user.UserRepository
import org.springframework.stereotype.Component

@Component
class UserService(
  private val userRepository: UserRepository
) {
  fun isExistsLoginId(loginId: LoginId): Boolean {
    val user = userRepository.findByLoginId(loginId = loginId.toString())
    return (user != null)
  }
}