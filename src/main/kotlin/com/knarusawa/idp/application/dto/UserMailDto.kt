package com.knarusawa.idp.application.dto

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "user_mail")
data class UserMailDto(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  val id: String? = null,

  @Column(name = "user_id")
  val userId: String = "",

  @Column(name = "email")
  val email: String = "",

  @Column(name = "is_verified", nullable = true)
  val isVerified: Boolean = false,

  @Column(name = "created_at")
  val createdAt: LocalDateTime = LocalDateTime.now(),

  @Column(name = "updated_at")
  val updatedAt: LocalDateTime = LocalDateTime.now()
)
