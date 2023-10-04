package com.knarusawa.idp.infrastructure.dto.response

import com.knarusawa.idp.infrastructure.middleware.logger
import org.springframework.boot.logging.LogLevel

data class ApiErrorResponse(
        val errorCode: String,
        val errorMessage: String
) {
    private val log = logger()

    companion object {
        fun of(exception: Exception, errorCode: String, errorMessage: String, logLevel: LogLevel) =
                ApiErrorResponse(
                        errorCode = errorCode,
                        errorMessage = errorMessage
                ).also { it.log(exception, logLevel, errorMessage) }
    }

    private fun log(ex: Exception, logLevel: LogLevel, message: String) {
        when (logLevel) {
            LogLevel.OFF -> Unit
            LogLevel.TRACE -> log.trace("$message, ex: ${ex.message} cause:${ex.cause}")
            LogLevel.DEBUG -> log.debug("$message, ex: ${ex.message} cause:${ex.cause}")
            LogLevel.INFO -> log.info("$message, ex: ${ex.message} cause:${ex.cause}")
            LogLevel.WARN -> log.warn("$message, ex: ${ex.message} cause:${ex.cause}")
            LogLevel.ERROR -> log.error("$message, ex: ${ex.message} cause:${ex.cause}")
            LogLevel.FATAL -> log.error("$message, ex: ${ex.message} cause:${ex.cause}")
        }
    }
}
