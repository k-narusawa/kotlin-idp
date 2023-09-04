package com.knarusawa.idp.domain.repository

import com.knarusawa.idp.domain.model.userMail.UserMail

interface UserMailRepository {
  fun save(userMail: UserMail)
  fun findByUserId(userId: String): UserMail?

  fun findByEmail(email: String): UserMail?

  fun deleteByUserId(userId: String)
}