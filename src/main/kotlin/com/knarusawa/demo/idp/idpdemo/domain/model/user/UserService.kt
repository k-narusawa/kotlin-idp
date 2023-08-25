package com.knarusawa.demo.idp.idpdemo.domain.model.user

import com.knarusawa.demo.idp.idpdemo.domain.repository.user.UserRepository
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