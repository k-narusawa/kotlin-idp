package com.knarusawa.idp.application.service.registerUser

import com.knarusawa.idp.domain.model.error.ErrorCode
import com.knarusawa.idp.domain.model.error.IdpAppException
import com.knarusawa.idp.domain.model.user.LoginId
import com.knarusawa.idp.domain.model.user.Role
import com.knarusawa.idp.domain.model.user.User
import com.knarusawa.idp.domain.model.user.UserService
import com.knarusawa.idp.domain.model.userMail.EMail
import com.knarusawa.idp.domain.model.userMail.UserMail
import com.knarusawa.idp.domain.model.userMail.UserMailService
import com.knarusawa.idp.domain.repository.UserMailRepository
import com.knarusawa.idp.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserRegisterService(
  private val userService: UserService,
  private val userMailService: UserMailService,
  private val userRepository: UserRepository,
  private val userMailRepository: UserMailRepository
) {
  fun execute(input: UserRegisterInputData) {
    if (userService.isExistsLoginId(loginId = LoginId(input.loginId)))
      throw IdpAppException(
        errorCode = ErrorCode.USER_EXISTS,
        logMessage = "会員がすでに存在します。 ログインID: ${input.loginId}"
      )

    if (input.eMail.isNotBlank() && !userMailService.isVerifiable(email = EMail(input.eMail))) {
      throw IdpAppException(
        errorCode = ErrorCode.BAD_REQUEST,
        logMessage = "通知先に登録ずみのメールアドレスです。"
      )
    }

    val user = User.of(
      loginId = input.loginId,
      password = input.password,
      roles = listOf(Role.USER)
    )
    userRepository.save(user)

    // 会員登録時にメールアドレスを登録しない場合
    if (input.eMail.isBlank())
      return

    val userMail = UserMail.of(
      userId = user.userId,
      eMail = input.eMail
    )
    userMailRepository.save(userMail)
  }
}