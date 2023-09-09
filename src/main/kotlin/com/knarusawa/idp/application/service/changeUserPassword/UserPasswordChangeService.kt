package com.knarusawa.idp.application.service.changeUserPassword

import com.knarusawa.idp.domain.model.error.ErrorCode
import com.knarusawa.idp.domain.model.error.IdpAppException
import com.knarusawa.idp.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserPasswordChangeService(
  private val userRepository: UserRepository
) {
  fun execute(input: UserPasswordChangeInputData) {
    val user = userRepository.findByUserId(userId = input.userId)
      ?: throw IdpAppException(
        errorCode = ErrorCode.USER_NOT_FOUND,
        logMessage = "会員が見つかりませんでした。"
      )

    user.changePassword(password = input.password)
    userRepository.save(user)
  }
}