package com.knarusawa.idp.infrastructure.adapter.db.repository.userMail

import com.knarusawa.idp.domain.model.userMail.UserMail
import com.knarusawa.idp.domain.repository.UserMailRepository
import com.knarusawa.idp.infrastructure.adapter.db.record.UserMailRecord
import org.springframework.stereotype.Repository

@Repository
class UserMailRepositoryImpl(
  private val userMailRecordRepository: UserMailRecordRepository
) : UserMailRepository {
  override fun save(userMail: UserMail) {
    userMailRecordRepository.save(UserMailRecord.from(userMail))
  }

  override fun findByUserId(userId: String): UserMail? {
    val record = userMailRecordRepository.findByUserId(userId = userId)
      ?: return null

    return UserMail.from(record)
  }

  override fun findByEmail(email: String): UserMail? {
    val record = userMailRecordRepository.findByEmail(eMail = email)
      ?: return null
    return UserMail.from(record)
  }
}