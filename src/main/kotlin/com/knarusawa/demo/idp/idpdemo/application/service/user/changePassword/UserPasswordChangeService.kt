package com.knarusawa.demo.idp.idpdemo.application.service.user.changePassword

import com.knarusawa.demo.idp.idpdemo.domain.model.error.AppException
import com.knarusawa.demo.idp.idpdemo.domain.model.error.ErrorCode
import com.knarusawa.demo.idp.idpdemo.domain.model.user.User
import com.knarusawa.demo.idp.idpdemo.domain.repository.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserPasswordChangeService(
  private val userRepository: UserRepository
) {
  fun execute(input: UserPasswordChangeInputData) {
    val user = userRepository.findByUserId(userId = input.userId)?.run {
      User.from(this)
    } ?: throw AppException(
      errorCode = ErrorCode.USER_NOT_FOUND,
      logMessage = "User Not Found"
    )
    user.updatePassword(password = input.password)
    userRepository.save(user.toEntity())
  }
}