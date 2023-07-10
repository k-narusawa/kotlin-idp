package com.knarusawa.demo.idp.idpdemo.application.service.user.getByUserId

import com.knarusawa.demo.idp.idpdemo.domain.model.error.AppException
import com.knarusawa.demo.idp.idpdemo.domain.model.error.ErrorCode
import com.knarusawa.demo.idp.idpdemo.domain.repository.user.UserReadModelRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserGetByUserIdService(
  private val userReadModelRepository: UserReadModelRepository
) {
  fun execute(userId: String): UserGetByIdOutputData {
    val user = userReadModelRepository.findByUserId(userId = userId) ?: throw AppException(
      errorCode = ErrorCode.USER_NOT_FOUND,
      logMessage = "User Not Found"
    )
    return UserGetByIdOutputData(user = user)
  }
}