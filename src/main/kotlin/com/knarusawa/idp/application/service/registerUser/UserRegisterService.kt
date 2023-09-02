package com.knarusawa.idp.application.service.registerUser

import com.knarusawa.idp.domain.model.error.AppException
import com.knarusawa.idp.domain.model.error.ErrorCode
import com.knarusawa.idp.domain.model.user.LoginId
import com.knarusawa.idp.domain.model.user.Role
import com.knarusawa.idp.domain.model.user.User
import com.knarusawa.idp.domain.model.user.UserService
import com.knarusawa.idp.domain.repository.user.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserRegisterService(
  private val userService: UserService,
  private val userRepository: UserRepository,
) {
  fun execute(input: UserRegisterInputData) {
    if (userService.isExistsLoginId(loginId = LoginId(input.loginId)))
      throw AppException(
        errorCode = ErrorCode.USER_EXISTS,
        logMessage = "User already exists. loginId: ${input.loginId}"
      )
    val user = User.of(
      loginId = input.loginId,
      password = input.password,
      roles = listOf(Role.USER)
    )
    userRepository.save(user.toEntity())
  }
}