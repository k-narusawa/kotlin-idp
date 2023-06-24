package com.knarusawa.demo.idp.idpdemo.application.mapper

import com.knarusawa.demo.idp.idpdemo.application.dto.UserResponse
import com.knarusawa.demo.idp.idpdemo.domain.model.user.User

object UserMapper {
  fun fromUser(user: User) = UserResponse(
    userId = user.userId,
    isLock = user.isLock,
    failedAttempts = user.failedAttempts,
    lockTime = user.lockTime,
    isDisabled = user.isDisabled,
    roles = user.roles.map { it.name },
    createdAd = user.createdAt,
    updatedAt = user.updatedAt,
  )
}