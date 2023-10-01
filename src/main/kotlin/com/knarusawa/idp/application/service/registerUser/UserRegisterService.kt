package com.knarusawa.idp.application.service.registerUser

import com.knarusawa.idp.domain.model.user.UserService
import com.knarusawa.idp.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserRegisterService(
  private val userService: UserService,
  private val userRepository: UserRepository,
) {
  fun execute(input: UserRegisterInputData) {

//    if (userService.isExistsLoginId(loginId = LoginId(input.loginId)))
//      throw IdpAppException(
//        errorCode = ErrorCode.USER_EXISTS,
//        logMessage = "会員がすでに存在します。 ログインID: ${input.loginId}"
//      )
//
//    val user = User.of(
//      loginId = input.loginId,
//      password = input.password,
//      roles = listOf(Role.USER)
//    )
//    userRepository.save(user)
  }
}