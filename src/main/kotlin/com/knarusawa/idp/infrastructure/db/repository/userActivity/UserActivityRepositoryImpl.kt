package com.knarusawa.idp.infrastructure.db.repository.userActivity

import com.knarusawa.idp.domain.model.user_activity.UserActivity
import com.knarusawa.idp.domain.repository.UserActivityRepository
import org.springframework.stereotype.Repository


@Repository
class UserActivityRepositoryImpl(
  private val userActivityRecordRepository: UserActivityRecordRepository
) : UserActivityRepository {
  override fun save(userActivity: UserActivity) {
    userActivityRecordRepository.save(userActivity.toRecord())
  }
}