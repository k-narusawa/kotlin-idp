package com.knarusawa.idp.application.service.changeUserLoginId

import com.knarusawa.idp.application.service.query.UserDtoQueryService
import com.knarusawa.idp.domain.model.IdpAppException
import com.knarusawa.idp.domain.model.UserActivity
import com.knarusawa.idp.domain.repository.UserActivityRepository
import com.knarusawa.idp.domain.repository.UserRepository
import com.knarusawa.idp.domain.service.UserService
import com.knarusawa.idp.domain.value.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ChangeUserLoginIdService(private val userService: UserService, private val userRepository: UserRepository, private val userActivityRepository: UserActivityRepository, private val userDtoQueryService: UserDtoQueryService) {
    fun execute(input: ChangeUserLoginIdInputData) {
        val user = userRepository.findByUserId(userId = input.userId)
                ?: throw IdpAppException(errorCode = ErrorCode.USER_NOT_FOUND, logMessage = "対象の会員が見つかりませんでした")

        if (userService.isExistsLoginId(loginId = LoginId(input.loginId))) throw IdpAppException(errorCode = ErrorCode.USER_EXISTS, logMessage = "すでに会員が存在します。 ログインID: ${input.loginId}")

        user.changeLoginId(loginId = input.loginId)
        userRepository.save(user)

        val newUser = userDtoQueryService.findByUserId(userId = input.userId)
                ?: throw IdpAppException(logMessage = "対象の会員が見つかりませんでした", errorCode = ErrorCode.INTERNAL_SERVER_ERROR)

        if (newUser.loginId != input.loginId) throw IdpAppException(logMessage = "データの不整合が発生しました", errorCode = ErrorCode.INTERNAL_SERVER_ERROR)

        storeActivity(userId = input.userId)
    }

    private fun storeActivity(userId: String) {
        val activity = UserActivity.of(userId = UserId(value = userId), activityType = ActivityType.CHANGE_LOGIN_ID, activityData = ActivityData(value = null))
        userActivityRepository.save(activity)
    }
}