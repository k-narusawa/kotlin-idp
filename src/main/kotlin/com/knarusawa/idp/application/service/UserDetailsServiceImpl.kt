package com.knarusawa.idp.application.service

import com.knarusawa.idp.domain.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

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

    val otp = generateOtpDigit()
    println("otp: $otp")
    // TODO: OTPを宛先に送る

    return org.springframework.security.core.userdetails.User
      .withUsername(user.userId.toString())
      .password(user.password.value)
      .roles(*user.roles.map { it.name }.toTypedArray())
      .accountLocked(user.isLock)
      .disabled(user.isDisabled)
      .authorities(Collections.emptyList()).build()
  }

  private fun generateOtpDigit(): String {
    val random = Random()
    val sb = StringBuilder()
    for (i in 0..5) {
      sb.append(random.nextInt(10))
    }
    return sb.toString()
  }
}