package com.knarusawa.demo.idp.idpdemo.application.service.event

import com.knarusawa.demo.idp.idpdemo.domain.model.user.User
import com.knarusawa.demo.idp.idpdemo.domain.repository.user.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.stereotype.Component

@Component
class AuthenticationEvents(
  private val userRepository: UserRepository
) {
  companion object {
    val logger = LoggerFactory.getLogger(AuthenticationEvents::class.java)
  }

  @EventListener
  fun onSuccess(success: AuthenticationSuccessEvent?) {
    val userId = success?.authentication?.name
    val user = userId?.let { userRepository.findByUserId(userId = it) }?.let { User.from(it) }
    if (user != null) {
      user.authSuccess()
      userRepository.save(user.toEntity())
    }
    logger.debug("ログイン成功 userId: $userId")
  }

  @EventListener
  fun onFailure(failures: AbstractAuthenticationFailureEvent?) {
    val loginId = failures?.authentication?.name.toString()
    val user = userRepository.findByLoginId(loginId = loginId)?.let { User.from(it) }
    if (user != null) {
      user.authFailed()
      userRepository.save(user.toEntity())
    }
    logger.debug("ログイン失敗 loginId: $loginId")
  }
}