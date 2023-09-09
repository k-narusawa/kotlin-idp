package com.knarusawa.idp.application.service.changeUserLoginId

import com.knarusawa.idp.application.service.query.UserDtoQueryService
import com.knarusawa.idp.domain.model.error.ErrorCode
import com.knarusawa.idp.domain.model.error.IdpAppException
import com.knarusawa.idp.domain.model.user.LoginId
import com.knarusawa.idp.domain.model.user.UserService
import com.knarusawa.idp.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserLoginIdChangeService(
  private val userService: UserService,
  private val userRepository: UserRepository,
  private val userDtoQueryService: UserDtoQueryService
) {
  fun execute(input: UserLoginIdChangeInputData): UserLoginIdChangeOutputData {
    val user = userRepository.findByUserId(userId = input.userId)
      ?: throw IdpAppException(
        errorCode = ErrorCode.USER_NOT_FOUND,
        logMessage = "対象の会員が見つかりませんでした"
      )

    if (userService.isExistsLoginId(loginId = LoginId(input.loginId)))
      throw IdpAppException(
        errorCode = ErrorCode.USER_EXISTS,
        logMessage = "すでに会員が存在します。 ログインID: ${input.loginId}"
      )

    user.changeLoginId(loginId = input.loginId)
    userRepository.save(user)

    val newUser = userDtoQueryService.findByUserId(userId = input.userId)
      ?: throw IdpAppException(
        logMessage = "対象の会員が見つかりませんでした",
        errorCode = ErrorCode.INTERNAL_SERVER_ERROR
      )

    if (newUser.loginId != input.loginId)
      throw IdpAppException(
        logMessage = "データの不整合が発生しました",
        errorCode = ErrorCode.INTERNAL_SERVER_ERROR
      )
    return UserLoginIdChangeOutputData(newUser)
  }
}