package com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity

import java.time.LocalDateTime

data class RoleRecord(
  val roleId: Int?,
  val userId: String,
  val role: String,
  val createdAt: LocalDateTime?,
  val updatedAt: LocalDateTime?,
)
