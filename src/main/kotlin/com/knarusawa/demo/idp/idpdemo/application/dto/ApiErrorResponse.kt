package com.knarusawa.demo.idp.idpdemo.application.dto

import org.slf4j.LoggerFactory
import org.springframework.boot.logging.LogLevel

data class ApiErrorResponse(
  val errorCode: String,
  val errorMessage: String
) {
  companion object {
    private val logger = LoggerFactory.getLogger(ApiErrorResponse::class.java)

    fun of(errorCode: String, errorMessage: String, loglevel: LogLevel) =
      ApiErrorResponse(
        errorCode = errorCode,
        errorMessage = errorMessage
      ).also { it.log(loglevel, errorMessage) }
  }

  private fun log(loglevel: LogLevel, message: String) {
    when (loglevel) {
      LogLevel.OFF -> Unit
      LogLevel.TRACE -> logger.trace(message)
      LogLevel.DEBUG -> logger.debug(message)
      LogLevel.INFO -> logger.info(message)
      LogLevel.WARN -> logger.warn(message)
      LogLevel.ERROR -> logger.error(message)
      LogLevel.FATAL -> logger.error(message)
    }
  }
}
