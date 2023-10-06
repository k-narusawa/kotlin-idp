package com.knarusawa.idp.application.service

import com.knarusawa.idp.application.facade.MassageSenderFacade
import com.knarusawa.idp.domain.model.IdpGrantedAuthority
import com.knarusawa.idp.domain.model.OneTimePassword
import com.knarusawa.idp.domain.repository.OnetimePasswordRepository
import com.knarusawa.idp.domain.repository.UserMfaRepository
import com.knarusawa.idp.domain.repository.UserRepository
import com.knarusawa.idp.domain.value.MessageId
import com.knarusawa.idp.domain.value.MfaType
import com.knarusawa.idp.infrastructure.middleware.logger
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
    private val log = logger()

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

        user.setAuthorities(authorities)

        if (userMfa?.type == MfaType.MAIL || userMfa?.type == MfaType.SMS) {
            val oneTimePassword = OneTimePassword.of(userId = user.userId)
            log.info("ワンタイムパスワード: ${oneTimePassword.code}")
            runBlocking { onetimePasswordRepository.save(oneTimePassword) }

            messageSenderFacade.exec(
                    toAddress = user.loginId.toString(),
                    messageId = MessageId.MFA_MAIL_AUTHENTICATION,
                    variables = listOf(Pair("#{otp}", oneTimePassword.code.toString()))
            )
        }

        return user
    }
}