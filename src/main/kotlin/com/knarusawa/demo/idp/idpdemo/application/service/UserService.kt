package com.knarusawa.demo.idp.idpdemo.application.service

import com.knarusawa.demo.idp.idpdemo.domain.model.user.User
import com.knarusawa.demo.idp.idpdemo.domain.repository.RoleRepository
import com.knarusawa.demo.idp.idpdemo.domain.repository.UserRepository
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.RoleEntity
import com.knarusawa.demo.idp.idpdemo.infrastructure.db.entity.UserEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
  private val userRepository: UserRepository,
  private val roleRepository: RoleRepository,
) {
  fun registerUser(user: User) {
    val userEntity = UserEntity(
      userId = user.userId,
      loginId = user.loginId,
      password = user.password,
      isLock = user.isLock,
      isDisabled = user.isDisabled,
      failedAttempts = user.failedAttempts,
      lockTime = user.lockTime,
      createdAt = user.createdAt,
      updatedAt = user.updatedAt
    )
    userRepository.save(userEntity)
    user.roles.forEach { role ->
      roleRepository.save(
        RoleEntity(
          roleId = 0,
          userId = user.userId,
          role = role.name,
          createdAt = null,
          updatedAt = null
        )
      )
    }
  }

  fun getByUserId(userId: String): User {
    val userEntity = userRepository.findByUserId(userId = userId) ?: throw RuntimeException("user not found")
    val roleEntity = roleRepository.findByUserId(userId = userId)
    return User.of(
      userEntity = userEntity,
      roleEntities = roleEntity
    )
  }

  fun getAllUsers(): List<User> {
    val userEntities = userRepository.findAll()
    return userEntities.map { User.of(it, roleRepository.findByUserId(it.userId)) }
  }
}