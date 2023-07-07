package com.knarusawa.demo.idp.idpdemo.application.service.user.loginIdUpdate

import com.knarusawa.demo.idp.idpdemo.domain.model.error.AppException
import com.knarusawa.demo.idp.idpdemo.domain.model.error.ErrorCode
import com.knarusawa.demo.idp.idpdemo.domain.model.user.LoginId
import com.knarusawa.demo.idp.idpdemo.domain.model.user.User
import com.knarusawa.demo.idp.idpdemo.domain.repository.UserRepository
import com.knarusawa.demo.idp.idpdemo.domain.service.UserDomainService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserLoginIdUpdateService(
    private val userDomainService: UserDomainService,
    private val userRepository: UserRepository
) {
  fun execute(input: UserLoginIdUpdateInputData): User {
    val user = userRepository.findByUserId(userId = input.userId.toString()) ?: throw AppException(
        errorCode = ErrorCode.USER_NOT_FOUND,
        logMessage = "User Not Found"
    )
    if (userDomainService.isExistsLoginId(loginId = LoginId(input.loginId)))
      throw AppException(
          errorCode = ErrorCode.USER_EXISTS,
          logMessage = "User already exists. loginId: ${input.loginId}"
      )
    val updatedUser = user.updateLoginId(loginId = input.loginId)
    userRepository.update(updatedUser)
    return updatedUser
  }
}