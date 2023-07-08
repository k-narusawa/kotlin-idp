package com.knarusawa.demo.idp.idpdemo.application.service.user.getById

import com.knarusawa.demo.idp.idpdemo.domain.model.error.AppException
import com.knarusawa.demo.idp.idpdemo.domain.model.error.ErrorCode
import com.knarusawa.demo.idp.idpdemo.domain.model.user.UserReadModel
import com.knarusawa.demo.idp.idpdemo.domain.repository.user.UserReadModelRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserGetByUserIdService(
  private val userReadModelRepository: UserReadModelRepository
) {
  fun execute(userId: String): UserReadModel {
    return userReadModelRepository.findByUserId(userId = userId) ?: throw AppException(
      errorCode = ErrorCode.USER_NOT_FOUND,
      logMessage = "User Not Found"
    )
  }
}