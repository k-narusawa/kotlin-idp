package com.knarusawa.idp.application.dto

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime


@Entity
@Table(name = "user_activity")
data class UserActivityDto(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id: String = "",

        @Column(name = "user_id")
        val userId: String = "",

        @Column(name = "activity_type")
        val activityType: String = "",

        @Column(name = "activity_data", nullable = true)
        val activityData: String? = "{}",

        @Column(name = "timestamp")
        val timestamp: LocalDateTime = LocalDateTime.now(),

        @CreatedDate
        @Column(name = "created_at", nullable = false, insertable = false, updatable = false)
        val createdAt: LocalDateTime = LocalDateTime.now(),

        @LastModifiedDate
        @Column(name = "updated_at", nullable = false, insertable = false, updatable = false)
        val updatedAt: LocalDateTime = LocalDateTime.now(),
)
