package com.knarusawa.idp.application.dto

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "user")
data class UserDto(
  @Id
  @Column(name = "user_id")
  val userId: String = "",

  @Column(name = "login_id")
  val loginId: String = "",

  @Column(name = "roles")
  val roles: String = "",

  @Column(name = "is_lock")
  val isLock: Boolean = false,

  @Column(name = "failed_attempts")
  val failedAttempts: Int? = null,

  @Column(name = "lock_time")
  val lockTime: LocalDateTime? = null,

  @Column(name = "is_disabled")
  val isDisabled: Boolean = false,

  @Column(name = "created_at")
  val createdAt: LocalDateTime = LocalDateTime.now(),

  @Column(name = "updated_at")
  val updatedAt: LocalDateTime = LocalDateTime.now()
)