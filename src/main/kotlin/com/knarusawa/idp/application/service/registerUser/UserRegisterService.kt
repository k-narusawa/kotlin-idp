package com.knarusawa.idp.application.service.registerUser

import com.knarusawa.idp.domain.model.error.ErrorCode
import com.knarusawa.idp.domain.model.error.IdpAppException
import com.knarusawa.idp.domain.model.oneTimePassword.Code
import com.knarusawa.idp.domain.model.user.LoginId
import com.knarusawa.idp.domain.model.user.Role
import com.knarusawa.idp.domain.model.user.User
import com.knarusawa.idp.domain.model.user.UserService
import com.knarusawa.idp.domain.repository.TmpUserRepository
import com.knarusawa.idp.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserRegisterService(
  private val userService: UserService,
  private val userRepository: UserRepository,
  private val tmpUserRepository: TmpUserRepository,
) {
  @Transactional
  fun execute(input: UserRegisterInputData) {
    val tmpUser = tmpUserRepository.findByCode(code = Code(input.code))
      ?: throw IdpAppException(
        errorCode = ErrorCode.USER_NOT_FOUND,
        logMessage = "仮会員が見つかりません"
      )

    if (userService.isExistsLoginId(loginId = LoginId(tmpUser.loginId.toString())))
      throw IdpAppException(
        errorCode = ErrorCode.USER_EXISTS,
        logMessage = "会員がすでに存在します"
      )

    val user = User.of(
      loginId = tmpUser.loginId.toString(),
      password = input.password,
      roles = listOf(Role.USER)
    )
    userRepository.save(user)
  }
}