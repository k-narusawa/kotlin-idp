package com.knarusawa.idp.domain.repository

import com.knarusawa.idp.domain.model.user_activity.UserActivity

interface UserActivityRepository {
  fun save(userActivity: UserActivity)
}