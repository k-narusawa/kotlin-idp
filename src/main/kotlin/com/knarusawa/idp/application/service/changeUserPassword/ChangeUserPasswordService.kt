package com.knarusawa.idp.application.service.changeUserPassword

import com.knarusawa.idp.domain.model.error.ErrorCode
import com.knarusawa.idp.domain.model.error.IdpAppException
import com.knarusawa.idp.domain.model.user.UserId
import com.knarusawa.idp.domain.model.userActivity.ActivityData
import com.knarusawa.idp.domain.model.userActivity.ActivityType
import com.knarusawa.idp.domain.model.userActivity.UserActivity
import com.knarusawa.idp.domain.repository.UserActivityRepository
import com.knarusawa.idp.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ChangeUserPasswordService(
  private val userRepository: UserRepository,
  private val userActivityRepository: UserActivityRepository
) {
  fun execute(input: ChangeUserPasswordInputData) {
    val user = userRepository.findByUserId(userId = input.userId)
      ?: throw IdpAppException(
        errorCode = ErrorCode.USER_NOT_FOUND,
        logMessage = "会員が見つかりませんでした。"
      )

    user.changePassword(password = input.password)

    userRepository.save(user)

    storeUserActivity(userId = input.userId)
  }

  private fun storeUserActivity(userId: String) {
    val activity = UserActivity.of(
      userId = UserId(value = userId),
      activityType = ActivityType.CHANGE_PASSWORD,
      activityData = ActivityData(value = null)
    )
    userActivityRepository.save(activity)
  }
}