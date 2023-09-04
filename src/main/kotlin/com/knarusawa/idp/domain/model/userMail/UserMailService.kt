package com.knarusawa.idp.domain.model.userMail

import com.knarusawa.idp.domain.repository.UserMailRepository
import org.springframework.stereotype.Service


@Service
class UserMailService(
  private val userMailRepository: UserMailRepository
) {
  fun isVerifiable(email: EMail): Boolean {
    val userMail =
      userMailRepository.findByEmail(email.toString()) ?: return true // 他の会員に使用されていなければ承認可能

    // 他の会員に登録されていても、対象のメールアドレスが承認されていなければ承認可能
    if (!userMail.isVerified) {
      return true
    }

    return false
  }
}