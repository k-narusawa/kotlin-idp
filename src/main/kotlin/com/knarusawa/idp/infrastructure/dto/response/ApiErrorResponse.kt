package com.knarusawa.idp.infrastructure.dto.response

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
            LogLevel.TRACE -> logger.trace("$message, ex: ${ex.message} cause:${ex.cause}")
            LogLevel.DEBUG -> logger.debug("$message, ex: ${ex.message} cause:${ex.cause}")
            LogLevel.INFO -> logger.info("$message, ex: ${ex.message} cause:${ex.cause}")
            LogLevel.WARN -> logger.warn("$message, ex: ${ex.message} cause:${ex.cause}")
            LogLevel.ERROR -> logger.error("$message, ex: ${ex.message} cause:${ex.cause}")
            LogLevel.FATAL -> logger.error("$message, ex: ${ex.message} cause:${ex.cause}")
        }
    }
}
