package com.knarusawa.idp.infrastructure.adapter.db.record

import com.knarusawa.idp.infrastructure.middleware.DbColumnEncryptDecryptConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.ColumnTransformer
import java.time.LocalDateTime

@Entity
@Table(name = "user")
data class UserRecord(
        @Id
        @Column(name = "user_id")
        val userId: String = "",

        @Column(name = "login_id")
        @Convert(converter = DbColumnEncryptDecryptConverter::class)
        val loginId: String = "",

        @Convert(converter = DbColumnEncryptDecryptConverter::class)
        @Column(name = "password")
        val password: String = "",

        @Column(name = "roles")
        val roles: String = "",

        @Column(name = "is_lock")
        val isLock: Boolean = false,

        @Column(name = "failed_attempts")
        val failedAttempts: Int = 0,

        @Column(name = "lock_time")
        val lockTime: LocalDateTime? = null,

        @Column(name = "is_disabled")
        val isDisabled: Boolean = false,

        @Column(name = "created_at", insertable = false, updatable = false)
        val createdAt: LocalDateTime? = null,

        @Column(name = "updated_at", insertable = false, updatable = false)
        val updatedAt: LocalDateTime? = null
)