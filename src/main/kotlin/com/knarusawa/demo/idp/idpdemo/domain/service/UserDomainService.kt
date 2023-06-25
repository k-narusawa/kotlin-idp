package com.knarusawa.demo.idp.idpdemo.domain.service

import com.knarusawa.demo.idp.idpdemo.domain.model.user.LoginId
import com.knarusawa.demo.idp.idpdemo.domain.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserDomainService(
  private val userRepository: UserRepository,
) {
  fun isExistsLoginId(loginId: LoginId): Boolean {
    val user = userRepository.findByLoginId(loginId = loginId.toString())
    return (user != null)
  }
}