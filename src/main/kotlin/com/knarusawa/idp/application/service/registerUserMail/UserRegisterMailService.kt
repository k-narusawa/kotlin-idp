package com.knarusawa.idp.application.service.registerUserMail

import com.knarusawa.idp.domain.model.user.UserId
import com.knarusawa.idp.domain.model.userMail.UserMail
import com.knarusawa.idp.domain.model.userMail.UserMailService
import com.knarusawa.idp.domain.repository.UserMailRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class UserRegisterMailService(
  private val userMailService: UserMailService,
  private val userMailRepository: UserMailRepository
) {
  companion object {
    val logger: Logger =
      LoggerFactory.getLogger(com.knarusawa.idp.application.event.AuthenticationEvents::class.java)
  }

  fun execute(inputData: UserRegisterMailInputData) {
    val userMail = UserMail.of(
      userId = UserId(value = inputData.userId),
      eMail = inputData.email
    )

    val isVerifiable = userMailService.isVerifiable(email = userMail.eMail)

    if (isVerifiable) {
      logger.info("確認メールを送信 userId: ${inputData.userId}")
    } else {
      logger.info("確認失敗メールを送信 userId: ${inputData.userId}")
    }

    userMailRepository.save(userMail)
  }
}