package com.knarusawa.demo.idp.idpdemo.domain.model.user

import com.knarusawa.demo.idp.idpdemo.domain.repository.user.UserReadModelRepository
import org.springframework.stereotype.Component

@Component
class UserService(
  private val userReadModelRepository: UserReadModelRepository
) {
  fun isExistsLoginId(loginId: LoginId): Boolean {
    val user = userReadModelRepository.findByLoginId(loginId = loginId.toString())
    return (user != null)
  }
}