package com.knarusawa.idp.application.dto

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

@Entity
@Table(name = "user_mfa")
data class UserMfaDto(
  @Id
  @Column(name = "user_id")
  val userId: String = "",

  @Column(name = "type")
  val mfaType: String = "",

  @Column(name = "target", nullable = true)
  val target: String? = "",

  @CreatedDate
  @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
  val createdAt: LocalDateTime = LocalDateTime.now(),

  @LastModifiedDate
  @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
  val updatedAt: LocalDateTime = LocalDateTime.now(),
)