package com.knarusawa.demo.idp.idpdemo.application.service.user.register

import com.knarusawa.demo.idp.idpdemo.domain.model.error.AppException
import com.knarusawa.demo.idp.idpdemo.domain.model.error.ErrorCode
import com.knarusawa.demo.idp.idpdemo.domain.model.user.LoginId
import com.knarusawa.demo.idp.idpdemo.domain.model.user.Role
import com.knarusawa.demo.idp.idpdemo.domain.model.user.User
import com.knarusawa.demo.idp.idpdemo.domain.repository.UserRepository
import com.knarusawa.demo.idp.idpdemo.domain.service.UserDomainService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserRegisterService(
    private val userDomainService: UserDomainService,
    private val userRepository: UserRepository,
) {
  fun execute(command: UserRegisterCommand) {
    if (userDomainService.isExistsLoginId(loginId = LoginId(command.loginId)))
      throw AppException(
          errorCode = ErrorCode.USER_EXISTS,
          logMessage = "User already exists. loginId: ${command.loginId}"
      )
    val user = User.new(
        loginId = command.loginId,
        password = command.password,
        roles = command.roles.map { Role.fromString(it) }
    )
    userRepository.save(user)
  }
}