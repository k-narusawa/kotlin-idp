package com.knarusawa.idp.infrastructure.adapter.db.record

import com.knarusawa.idp.domain.model.userMail.UserMail
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "user_mail")
data class UserMailRecord(
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

  @Column(name = "created_at", insertable = false, updatable = false)
  val createdAt: LocalDateTime? = null,

  @Column(name = "updated_at", insertable = false, updatable = false)
  val updatedAt: LocalDateTime? = null
) {
  companion object {
    fun from(userMail: UserMail) = UserMailRecord(
      userId = userMail.userId.toString(),
      email = userMail.eMail.toString(),
      isVerified = userMail.isVerified,
    )
  }
}
