package com.knarusawa.idp.application.service.changeUserLoginId

import com.knarusawa.idp.domain.model.IdpAppException
import com.knarusawa.idp.domain.model.UserActivity
import com.knarusawa.idp.domain.repository.LoginIdUpdateDateRepository
import com.knarusawa.idp.domain.repository.UserActivityRepository
import com.knarusawa.idp.domain.repository.UserRepository
import com.knarusawa.idp.domain.service.UserService
import com.knarusawa.idp.domain.value.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ChangeUserLoginIdService(
        private val userService: UserService,
        private val userRepository: UserRepository,
        private val userActivityRepository: UserActivityRepository,
        private val loginIdUpdateDateRepository: LoginIdUpdateDateRepository,
) {
    @Transactional
    fun execute(input: ChangeUserLoginIdInputData) {
        val data = loginIdUpdateDateRepository.findByKey(LoginIdUpdateKey.fromCode(input.code).toString())
                ?: throw IdpAppException(errorCode = ErrorCode.BAD_REQUEST, logMessage = "認証コードが間違っています")

        userService.isExistsLoginId(LoginId(data.loginId))

        val user = userRepository.findByUserId(data.userId)
                ?: throw IdpAppException(errorCode = ErrorCode.INTERNAL_SERVER_ERROR, logMessage = "ユーザーが見つかりませんでした")

        user.changeLoginId(data.loginId)
        storeActivity(userId = data.userId)
        userRepository.save(user)
    }

    private fun storeActivity(userId: String) {
        val activity = UserActivity.of(userId = UserId(value = userId), activityType = ActivityType.CHANGE_LOGIN_ID, activityData = ActivityData(value = null))
        userActivityRepository.save(activity)
    }
}