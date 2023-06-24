package com.knarusawa.demo.idp.idpdemo.application.service

import com.knarusawa.demo.idp.idpdemo.domain.model.error.AppException
import com.knarusawa.demo.idp.idpdemo.domain.model.error.ErrorCode
import com.knarusawa.demo.idp.idpdemo.domain.model.user.LoginId
import com.knarusawa.demo.idp.idpdemo.domain.model.user.User
import com.knarusawa.demo.idp.idpdemo.domain.model.user.UserUpdateCommand
import com.knarusawa.demo.idp.idpdemo.domain.repository.RoleRepository
import com.knarusawa.demo.idp.idpdemo.domain.repository.UserRepository
import com.knarusawa.demo.idp.idpdemo.domain.service.UserDomainService
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.RoleRecord
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.UserRecord
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
  private val userDomainService: UserDomainService,
  private val userRepository: UserRepository,
  private val roleRepository: RoleRepository,
) {
  fun registerUser(
    loginId: String,
    password: String,
    roles: List<String>
  ) {
    val user = User.of(
      loginId = loginId,
      password = password,
      roles = roles
    )
    val userRecord = UserRecord(
      userId = user.userId,
      loginId = user.loginId.toString(),
      password = user.password.toString(),
      isLock = user.isLock,
      isDisabled = user.isDisabled,
      failedAttempts = user.failedAttempts,
      lockTime = user.lockTime,
      createdAt = user.createdAt,
      updatedAt = user.updatedAt
    )
    userRepository.save(userRecord)
    user.roles.forEach { role ->
      roleRepository.save(
        RoleRecord(
          roleId = null,
          userId = user.userId,
          role = role.name,
          createdAt = null,
          updatedAt = null
        )
      )
    }
  }

  fun getByUserId(userId: String): User {
    val user =
      userRepository.findByUserId(userId = userId) ?: throw AppException(
        errorCode = ErrorCode.USER_NOT_FOUND,
        errorMessage = "User Not Found"
      )
    val roleEntity = roleRepository.findByUserId(userId = userId)
    return User.of(
      userRecord = user,
      roleEntities = roleEntity
    )
  }

  fun update(command: UserUpdateCommand) {
    val userRecord = userRepository.findByUserId(userId = command.userId)
      ?: throw AppException(
        errorCode = ErrorCode.USER_NOT_FOUND,
        errorMessage = "User Not Found"
      )
    val roleRecords = roleRepository.findByUserId(userId = command.userId)
    val user = User.of(userRecord, roleRecords)

    if (command.loginId != null) {
      if (userDomainService.isExistsLoginId(loginId = LoginId(value = command.loginId)))
        throw AppException(errorCode = ErrorCode.BAD_REQUEST, errorMessage = "User Already Exists")
      user.updateLoginId(loginId = command.loginId)
    }

  }

  fun getAllUsers(): List<User> {
    val userEntities = userRepository.findAll()
    return userEntities.map { User.of(it, roleRepository.findByUserId(it.userId)) }
  }
}