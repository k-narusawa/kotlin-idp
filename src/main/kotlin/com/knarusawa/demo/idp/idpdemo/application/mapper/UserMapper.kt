package com.knarusawa.demo.idp.idpdemo.application.mapper

import com.knarusawa.demo.idp.idpdemo.domain.model.user.User
import com.knarusawa.demo.idp.idpdemo.domain.model.userRole.Role
import com.knarusawa.demo.idp.idpdemo.infrastructure.dto.UserResponse

object UserMapper {
  fun fromUser(user: User, roles: List<Role>) = UserResponse(
    userId = user.userId,
    isLock = user.isLock,
    failedAttempts = user.failedAttempts,
    lockTime = user.lockTime,
    isDisabled = user.isDisabled,
    roles = roles.map { it.name },
    createdAd = user.createdAt,
    updatedAt = user.updatedAt,
  )
}