package com.knarusawa.demo.idp.idpdemo.application.mapper

import com.knarusawa.demo.idp.idpdemo.infrastructure.dto.UserDto
import com.knarusawa.demo.idp.idpdemo.infrastructure.dto.UserResponse

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