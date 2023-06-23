package com.knarusawa.demo.idp.idpdemo.domain.model.user

import com.knarusawa.demo.idp.idpdemo.configuration.SecurityConfig
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.RoleEntity
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.UserEntity
import java.time.LocalDateTime
import java.util.*

data class User(
  val userId: String,
  val loginId: String,
  val password: String,
  val isLock: Boolean,
  val failedAttempts: Int?,
  val lockTime: LocalDateTime?,
  val isDisabled: Boolean,
  val roles: List<Role>,
  val createdAt: LocalDateTime?,
  val updatedAt: LocalDateTime?
) {
  companion object {
    fun of(userEntity: UserEntity, roleEntities: List<RoleEntity>) =
      User(
        userId = userEntity.userId,
        loginId = userEntity.loginId,
        password = userEntity.password,
        isLock = userEntity.isLock,
        failedAttempts = userEntity.failedAttempts,
        lockTime = userEntity.lockTime,
        isDisabled = userEntity.isDisabled,
        roles = roleEntities.map { Role.fromString(it.role) },
        createdAt = userEntity.createdAt,
        updatedAt = userEntity.updatedAt
      )

    fun of(loginId: String, password: String, roles: List<String>) =
      User(
        userId = UUID.randomUUID().toString(),
        loginId = loginId,
        password = SecurityConfig().passwordEncoder().encode(password),
        isLock = false,
        failedAttempts = null,
        lockTime = null,
        isDisabled = false,
        roles = roles.map { Role.fromString(it) },
        createdAt = null,
        updatedAt = null
      )
  }
}
