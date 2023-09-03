package com.knarusawa.idp.infrastructure.db.record

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "user_activity")
data class UserActivityRecord(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  val id: String? = null,

  @Column(name = "user_id")
  val userId: String = "",

  @Column(name = "activity_type")
  val activityType: String = "",

  @Column(name = "activity_data", nullable = true)
  val activityData: String?,

  @Column(name = "timestamp")
  val timestamp: LocalDateTime = LocalDateTime.now(),

  @Column(name = "created_at", insertable = false, updatable = false)
  val createdAt: LocalDateTime? = null,

  @Column(name = "updated_at", insertable = false, updatable = false)
  val updatedAt: LocalDateTime? = null
)
