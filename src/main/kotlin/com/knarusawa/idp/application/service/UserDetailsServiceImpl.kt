package com.knarusawa.idp.application.service

import com.knarusawa.idp.application.event.AuthenticationEvents.Companion.logger
import com.knarusawa.idp.domain.model.authority.IdpGrantedAuthority
import com.knarusawa.idp.domain.model.oneTimePassword.OneTimePassword
import com.knarusawa.idp.domain.repository.OnetimePasswordRepository
import com.knarusawa.idp.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
  private val userRepository: UserRepository,
  private val onetimePasswordRepository: OnetimePasswordRepository
) : UserDetailsService {
  override fun loadUserByUsername(loginId: String): UserDetails {
    val user = userRepository.findByLoginId(loginId = loginId)
      ?: throw UsernameNotFoundException("認証に失敗しました")

    // ロックされてから30分経過していたらアンロックする
    user.unlockByTimeElapsed()
    userRepository.save(user)

    val isUsingMfa = false

    val authorities = when (isUsingMfa) {
      true -> listOf(IdpGrantedAuthority.useMfaMail())
      false -> listOf(IdpGrantedAuthority.usePassword())
    }

    if (isUsingMfa) { // TODO: MFA追加したらやる
      val oneTimePassword = OneTimePassword.of(userId = user.userId)
      logger.info("ワンタイムパスワード: ${oneTimePassword.code}")
      runBlocking { onetimePasswordRepository.save(oneTimePassword) }
      // TODO: OTPを宛先に送る
    }

    return org.springframework.security.core.userdetails.User
      .withUsername(user.userId.toString())
      .password(user.password.value)
      .roles(*user.roles.map { it.name }.toTypedArray())
      .accountLocked(user.isLock)
      .disabled(user.isDisabled)
      .authorities(authorities)
      .build()
  }
}