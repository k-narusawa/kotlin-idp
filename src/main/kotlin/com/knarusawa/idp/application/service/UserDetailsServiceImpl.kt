package com.knarusawa.idp.application.service

import com.knarusawa.idp.application.event.AuthenticationEvents.Companion.logger
import com.knarusawa.idp.application.facade.MassageSenderFacade
import com.knarusawa.idp.domain.model.authority.IdpGrantedAuthority
import com.knarusawa.idp.domain.model.messageTemplate.MessageId
import com.knarusawa.idp.domain.model.oneTimePassword.OneTimePassword
import com.knarusawa.idp.domain.model.userMfa.MfaType
import com.knarusawa.idp.domain.repository.OnetimePasswordRepository
import com.knarusawa.idp.domain.repository.UserMfaRepository
import com.knarusawa.idp.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
        private val userRepository: UserRepository,
        private val userMfaRepository: UserMfaRepository,
        private val onetimePasswordRepository: OnetimePasswordRepository,
        private val messageSenderFacade: MassageSenderFacade
) : UserDetailsService {
    override fun loadUserByUsername(loginId: String): UserDetails {
        val user = userRepository.findByLoginId(loginId = loginId)
                ?: throw UsernameNotFoundException("認証に失敗しました")

        // ロックされてから30分経過していたらアンロックする
        user.unlockByTimeElapsed()
        userRepository.save(user)

        val userMfa = userMfaRepository.findByUserId(userId = user.userId)

        val authorities = when (userMfa?.type) {
            MfaType.APP -> listOf(IdpGrantedAuthority.useMfaApp())
            MfaType.MAIL -> listOf(IdpGrantedAuthority.useMfaMail())
            MfaType.SMS -> listOf(IdpGrantedAuthority.useMfaSms())
            else -> listOf(IdpGrantedAuthority.usePassword())
        }

        if (userMfa?.type == MfaType.MAIL || userMfa?.type == MfaType.SMS) {
            val oneTimePassword = OneTimePassword.of(userId = user.userId)
            logger.info("ワンタイムパスワード: ${oneTimePassword.code}")
            runBlocking { onetimePasswordRepository.save(oneTimePassword) }

            messageSenderFacade.exec(
                    toAddress = user.loginId.toString(),
                    messageId = MessageId.MFA_MAIL_AUTHENTICATION,
                    variables = listOf(Pair("#{otp}", oneTimePassword.code.toString()))
            )
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.userId.toString())
                .password(user.password.toString())
                .roles(*user.roles.map { it.name }.toTypedArray())
                .accountLocked(user.isLock)
                .disabled(user.isDisabled)
                .authorities(authorities)
                .build()
    }
}