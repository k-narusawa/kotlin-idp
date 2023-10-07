package com.knarusawa.idp.infrastructure.middleware

import com.knarusawa.idp.domain.model.UserActivity
import com.knarusawa.idp.domain.repository.UserActivityRepository
import com.knarusawa.idp.domain.value.ActivityData
import com.knarusawa.idp.domain.value.ActivityType
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler

class IdpLogoutHandler(
        private val userActivityRepository: UserActivityRepository
) : LogoutHandler {

    private val log = logger()
    override fun logout(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        val user = authentication?.principal as? com.knarusawa.idp.domain.model.User

        if (user == null) {
            log.warn("ログアウト時にユーザー情報の取得に失敗しました")
            return
        }

        val activity = UserActivity.of(
                userId = user.userId,
                activityType = ActivityType.LOGOUT,
                activityData = ActivityData(value = null)
        )

        userActivityRepository.save(activity)

        log.info("ログアウト成功 userId:[${user.userId}]")
    }
}