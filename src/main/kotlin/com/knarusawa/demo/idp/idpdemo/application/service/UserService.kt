package com.knarusawa.demo.idp.idpdemo.application.service

import com.knarusawa.demo.idp.idpdemo.domain.model.error.AppException
import com.knarusawa.demo.idp.idpdemo.domain.model.error.ErrorCode
import com.knarusawa.demo.idp.idpdemo.domain.model.user.LoginId
import com.knarusawa.demo.idp.idpdemo.domain.model.user.User
import com.knarusawa.demo.idp.idpdemo.domain.model.user.command.UserRegisterCommand
import com.knarusawa.demo.idp.idpdemo.domain.model.user.command.UserUpdateCommand
import com.knarusawa.demo.idp.idpdemo.domain.model.userRole.Role
import com.knarusawa.demo.idp.idpdemo.domain.model.userRole.UserRole
import com.knarusawa.demo.idp.idpdemo.domain.repository.UserRepository
import com.knarusawa.demo.idp.idpdemo.domain.repository.UserRoleRepository
import com.knarusawa.demo.idp.idpdemo.domain.service.UserDomainService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
  private val userDomainService: UserDomainService,
  private val userRepository: UserRepository,
  private val userRoleRepository: UserRoleRepository,
) {
  fun registerUser(
    command: UserRegisterCommand
  ) {
    if (userDomainService.isExistsLoginId(loginId = LoginId(command.loginId)))
      throw AppException(
        errorCode = ErrorCode.USER_EXISTS,
        logMessage = "User already exists. loginId: ${command.loginId}"
      )
    val user = User.new(
      loginId = command.loginId,
      password = command.password,
    )
    userRepository.save(user)
    command.roles.forEach { role ->
      val userRole = UserRole.of(
        userId = user.userId,
        role = Role.fromString(role)
      )
      userRoleRepository.save(userRole)
    }
  }

  fun getByUserId(userId: String): User {
    return userRepository.findByUserId(userId = userId) ?: throw AppException(
      errorCode = ErrorCode.USER_NOT_FOUND,
      logMessage = "User Not Found"
    )
  }

  fun update(command: UserUpdateCommand) {
    val user = userRepository.findByUserId(userId = command.userId)
      ?: throw AppException(
        errorCode = ErrorCode.USER_NOT_FOUND,
        logMessage = "User Not Found"
      )

    if (command.loginId != null) {
      if (userDomainService.isExistsLoginId(loginId = LoginId(value = command.loginId)))
        throw AppException(errorCode = ErrorCode.BAD_REQUEST, logMessage = "User Already Exists")
      user.updateLoginId(loginId = command.loginId)
    }

  }

  fun getAllUsers(): List<User> {
    return userRepository.findAll()
  }
}