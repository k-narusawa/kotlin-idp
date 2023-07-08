package com.knarusawa.demo.idp.idpdemo.application.service.user.loginIdUpdate

import com.knarusawa.demo.idp.idpdemo.domain.model.error.AppException
import com.knarusawa.demo.idp.idpdemo.domain.model.error.ErrorCode
import com.knarusawa.demo.idp.idpdemo.domain.model.user.LoginId
import com.knarusawa.demo.idp.idpdemo.domain.model.user.UserReadModel
import com.knarusawa.demo.idp.idpdemo.domain.model.user.UserService
import com.knarusawa.demo.idp.idpdemo.domain.repository.user.UserReadModelRepository
import com.knarusawa.demo.idp.idpdemo.domain.repository.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserLoginIdUpdateService(
  private val userService: UserService,
  private val userRepository: UserRepository,
  private val userReadModelRepository: UserReadModelRepository
) {
  fun execute(input: UserLoginIdUpdateInputData): UserReadModel {
    val user = userRepository.findByUserId(userId = input.userId) ?: throw AppException(
      errorCode = ErrorCode.USER_NOT_FOUND,
      logMessage = "User Not Found"
    )

    if (userService.isExistsLoginId(loginId = LoginId(input.loginId)))
      throw AppException(
        errorCode = ErrorCode.USER_EXISTS,
        logMessage = "User already exists. loginId: ${input.loginId}"
      )

    userRepository.update(user.updateLoginId(loginId = input.loginId))

    val newUser = userReadModelRepository.findByUserId(userId = input.userId) ?: throw AppException(
      logMessage = "User Not Found",
      errorCode = ErrorCode.INTERNAL_SERVER_ERROR
    )

    if (newUser.loginId.toString() != input.loginId)
      throw AppException(
        logMessage = "Data inconsistency occurred",
        errorCode = ErrorCode.INTERNAL_SERVER_ERROR
      )

    return newUser
  }
}