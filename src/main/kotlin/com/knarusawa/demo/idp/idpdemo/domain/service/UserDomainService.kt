package com.knarusawa.demo.idp.idpdemo.domain.service

import com.knarusawa.demo.idp.idpdemo.domain.model.user.LoginId
import com.knarusawa.demo.idp.idpdemo.domain.repository.user.UserReadModelRepository
import org.springframework.stereotype.Component

@Component
class UserDomainService(
  private val userReadModelRepository: UserReadModelRepository
) {
  fun isExistsLoginId(loginId: LoginId): Boolean {
    val user = userReadModelRepository.findByLoginId(loginId = loginId.toString())
    return (user != null)
  }
}