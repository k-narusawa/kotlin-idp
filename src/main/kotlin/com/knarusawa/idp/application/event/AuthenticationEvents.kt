package com.knarusawa.idp.application.event

import com.knarusawa.idp.domain.model.UserActivity
import com.knarusawa.idp.domain.repository.UserActivityRepository
import com.knarusawa.idp.domain.repository.UserRepository
import com.knarusawa.idp.domain.value.ActivityData
import com.knarusawa.idp.domain.value.ActivityType
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
        val user = success?.authentication?.principal as? com.knarusawa.idp.domain.model.User
                ?: throw RuntimeException("ログインイベントからユーザーの取得に失敗しました")

        user.authSuccess()
        userRepository.save(user)
        val activity = UserActivity.of(
                userId = user.userId,
                activityType = ActivityType.LOGIN_SUCCESS,
                activityData = ActivityData(value = null)
        )
        userActivityRepository.save(activity)
        log.info("ログイン成功 userId: ${user.userId}")
    }

    @EventListener
    fun onFailure(failures: AbstractAuthenticationFailureEvent?) {
        val user = failures?.authentication?.principal as? com.knarusawa.idp.domain.model.User
                ?: throw RuntimeException("ログインイベントからユーザーの取得に失敗しました")

        user.authFailed()
        userRepository.save(user)
        val activity = UserActivity.of(
                userId = user.userId,
                activityType = ActivityType.LOGIN_FAILED,
                activityData = ActivityData(value = null)
        )
        userActivityRepository.save(activity)
        log.info("ログイン失敗 userId: ${user.userId}")
    }
}