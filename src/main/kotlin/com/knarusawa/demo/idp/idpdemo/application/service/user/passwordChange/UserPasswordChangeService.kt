package com.knarusawa.demo.idp.idpdemo.application.service.user.passwordChange

import com.knarusawa.demo.idp.idpdemo.domain.model.error.AppException
import com.knarusawa.demo.idp.idpdemo.domain.model.error.ErrorCode
import com.knarusawa.demo.idp.idpdemo.domain.repository.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserPasswordChangeService(
  private val userRepository: UserRepository
) {
  fun execute(input: UserPasswordChangeInputData) {
    val user = userRepository.findByUserId(userId = input.userId) ?: throw AppException(
      errorCode = ErrorCode.USER_NOT_FOUND,
      logMessage = "User Not Found"
    )
    userRepository.update(user.updatePassword(password = input.password))
  }
}