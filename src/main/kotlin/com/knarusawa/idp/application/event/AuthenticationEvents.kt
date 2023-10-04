package com.knarusawa.idp.application.event

import com.knarusawa.idp.domain.model.UserActivity
import com.knarusawa.idp.domain.repository.UserActivityRepository
import com.knarusawa.idp.domain.repository.UserRepository
import com.knarusawa.idp.domain.value.ActivityData
import com.knarusawa.idp.domain.value.ActivityType
import com.knarusawa.idp.domain.value.UserId
import com.knarusawa.idp.infrastructure.middleware.logger
import org.springframework.context.event.EventListener
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent
import org.springframework.security.authentication.event.AuthenticationSuccessEvent
import org.springframework.stereotype.Component

@Component
class AuthenticationEvents(
        private val userRepository: UserRepository,
        private val userActivityRepository: UserActivityRepository
) {
    private val log = logger()

    @EventListener
    fun onSuccess(success: AuthenticationSuccessEvent?) {
        val userId = success?.authentication?.name ?: return
        val user = userRepository.findByUserId(userId = userId)
        if (user != null) {
            user.authSuccess()
            userRepository.save(user)
            val activity = UserActivity.of(
                    userId = UserId(value = userId),
                    activityType = ActivityType.LOGIN_SUCCESS,
                    activityData = ActivityData(value = null)
            )
            userActivityRepository.save(activity)
        }
        log.debug("ログイン成功 userId: $userId")
    }

    @EventListener
    fun onFailure(failures: AbstractAuthenticationFailureEvent?) {
        val loginId = failures?.authentication?.name.toString()
        val user = userRepository.findByLoginId(loginId = loginId)
        if (user != null) {
            user.authFailed()
            userRepository.save(user)
            val activity = UserActivity.of(
                    userId = user.userId,
                    activityType = ActivityType.LOGIN_FAILED,
                    activityData = ActivityData(value = null)
            )
            userActivityRepository.save(activity)
        }
        log.debug("ログイン失敗 loginId: $loginId")
    }
}