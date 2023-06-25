package com.knarusawa.demo.idp.idpdemo.infrastructure.dto

import org.slf4j.LoggerFactory
import org.springframework.boot.logging.LogLevel

data class ApiErrorResponse(
  val errorCode: String,
  val errorMessage: String
) {
  companion object {
    private val logger = LoggerFactory.getLogger(ApiErrorResponse::class.java)

    fun of(exception: Exception, errorCode: String, errorMessage: String, logLevel: LogLevel) =
      ApiErrorResponse(
        errorCode = errorCode,
        errorMessage = errorMessage
      ).also { it.log(exception, logLevel, errorMessage) }
  }

  private fun log(ex: Exception, logLevel: LogLevel, message: String) {
    when (logLevel) {
      LogLevel.OFF -> Unit
      LogLevel.TRACE -> logger.trace("$message, ex: ${ex.message}")
      LogLevel.DEBUG -> logger.debug("$message, ex: ${ex.message}")
      LogLevel.INFO -> logger.info("$message, ex: ${ex.message}")
      LogLevel.WARN -> logger.warn("$message, ex: ${ex.message}")
      LogLevel.ERROR -> logger.error("$message, ex: ${ex.message}")
      LogLevel.FATAL -> logger.error("$message, ex: ${ex.message}")
    }
  }
}
