package com.knarusawa.idp.infrastructure.adapter.db.record

import com.knarusawa.idp.domain.model.UserWithdraw
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_withdraw")
data class UserWithdrawRecord(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        val id: String? = null,

        @Column(name = "user_id")
        val userId: String = "",

        @Column(name = "original_login_id")
        val originalLoginId: String = "",

        @Column(name = "withdrawn_at")
        val withdrawnAt: LocalDateTime = LocalDateTime.now(),

        @Column(name = "created_at", insertable = false, updatable = false)
        val createdAt: LocalDateTime? = null,

        @Column(name = "updated_at", insertable = false, updatable = false)
        val updatedAt: LocalDateTime? = null
) {
    companion object {
        fun from(userWithdraw: UserWithdraw) = UserWithdrawRecord(
                userId = userWithdraw.userId.toString(),
                originalLoginId = userWithdraw.originalLoginId.toString(),
                withdrawnAt = userWithdraw.withdrawnAt,

                )
    }
}