package com.knarusawa.idp.application.service.changeUserMail

import com.knarusawa.idp.domain.model.error.ErrorCode
import com.knarusawa.idp.domain.model.error.IdpAppException
import com.knarusawa.idp.domain.model.userMail.UserMailService
import com.knarusawa.idp.domain.repository.UserMailRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserMailChangeService(
  private val userMailService: UserMailService,
  private val userMailRepository: UserMailRepository
) {
  companion object {
    val logger: Logger =
      LoggerFactory.getLogger(com.knarusawa.idp.application.event.AuthenticationEvents::class.java)
  }

  fun execute(inputData: UserMailChangeInputData) {
    val userMail = userMailRepository.findByUserId(userId = inputData.userId)
      ?: throw IdpAppException(
        errorCode = ErrorCode.BAD_REQUEST,
        logMessage = "ユーザーが存在しません userId: ${inputData.userId}"
      )

    userMailRepository.deleteByUserId(userId = inputData.userId)

    userMail.changeEMail(email = inputData.email)
    val isVerifiable = userMailService.isVerifiable(email = userMail.eMail)

    if (isVerifiable) {
      logger.info("確認メールを送信 userId: ${inputData.userId}")
    } else {
      logger.info("確認失敗メールを送信 userId: ${inputData.userId}")
    }

    userMailRepository.save(userMail)
  }
}