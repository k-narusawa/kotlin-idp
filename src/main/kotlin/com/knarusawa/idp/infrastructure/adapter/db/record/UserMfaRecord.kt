package com.knarusawa.idp.infrastructure.adapter.db.record

import com.knarusawa.idp.domain.model.user.UserId
import com.knarusawa.idp.domain.model.userMfa.MfaType
import com.knarusawa.idp.domain.model.userMfa.UserMfa
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

  @Column(name = "target")
  val target: String = "",

  @Column(name = "created_at", insertable = false, updatable = false)
  val createdAt: LocalDateTime? = null,

  @Column(name = "updated_at", insertable = false, updatable = false)
  val updatedAt: LocalDateTime? = null
) {
  companion object {
    fun from(userMfa: UserMfa) = UserMfaRecord(
      userId = userMfa.userId.toString(),
      type = userMfa.type.toString(),
      target = userMfa.target,
    )
  }

  fun to() = UserMfa.of(
    userId = UserId(value = this.userId),
    type = MfaType.from(this.type),
    target = this.target
  )
}