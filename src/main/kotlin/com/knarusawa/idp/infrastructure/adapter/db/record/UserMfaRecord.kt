package com.knarusawa.idp.infrastructure.adapter.db.record

import com.knarusawa.idp.domain.model.UserMfa
import com.knarusawa.idp.domain.value.MfaType
import com.knarusawa.idp.domain.value.UserId
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "user_mfa")
data class UserMfaRecord(
        @Id
        @Column(name = "user_id")
        val userId: String = "",

        @Column(name = "type")
        val type: String = "",

        @Column(name = "secret_key")
        val secretKey: String? = null,

        @Column(name = "validation_code")
        val validationCode: Int? = null,

        @Column(name = "scratch_codes")
        val scratchCodes: String? = null,

        @Column(name = "created_at", insertable = false, updatable = false)
        val createdAt: LocalDateTime? = null,

        @Column(name = "updated_at", insertable = false, updatable = false)
        val updatedAt: LocalDateTime? = null
) {
    companion object {
        fun from(userMfa: UserMfa) = UserMfaRecord(
                userId = userMfa.userId.toString(),
                type = userMfa.type.toString(),
                secretKey = userMfa.secretKey,
                validationCode = userMfa.validationCode,
                scratchCodes = userMfa.scratchCodes?.joinToString(","),
        )
    }

    fun to() = UserMfa.of(
            userId = UserId(value = this.userId),
            type = MfaType.from(this.type),
            secretKey = secretKey,
            validationCode = validationCode,
            scratchCodes = if (scratchCodes.isNullOrEmpty()) listOf() else scratchCodes.split(",").map { it.toInt() }
    )
}
