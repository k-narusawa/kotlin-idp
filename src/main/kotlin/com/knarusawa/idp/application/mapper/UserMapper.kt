package com.knarusawa.idp.application.mapper

import com.knarusawa.idp.application.dto.UserDto
import com.knarusawa.idp.infrastructure.dto.UserResponse

object UserMapper {
  fun fromUser(user: UserDto) = UserResponse(
    userId = user.userId,
    roles = user.roles.map { it.toString() },
    isLock = user.isLock,
    failedAttempts = user.failedAttempts,
    lockTime = user.lockTime,
    isDisabled = user.isDisabled,
    createdAd = user.createdAt,
    updatedAt = user.updatedAt,
  )
}