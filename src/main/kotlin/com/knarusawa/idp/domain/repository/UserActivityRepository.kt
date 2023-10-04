package com.knarusawa.idp.domain.repository

import com.knarusawa.idp.domain.model.userActivity.UserActivity

interface UserActivityRepository {
    fun save(userActivity: UserActivity)
}