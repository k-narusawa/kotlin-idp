package com.knarusawa.idp.domain.repository

import com.knarusawa.idp.domain.model.UserActivity

interface UserActivityRepository {
    fun save(userActivity: UserActivity)
}