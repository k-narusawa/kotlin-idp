package com.knarusawa.idp.infrastructure.middleware

import com.knarusawa.idp.domain.model.IdpAppException
import com.knarusawa.idp.domain.value.ErrorCode
import com.knarusawa.idp.infrastructure.dto.response.ApiErrorResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.logging.LogLevel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {
    private val log = logger()

    @ExceptionHandler(IdpAppException::class)
    fun handleIdpAppException(
            ex: IdpAppException,
            request: HttpServletRequest
    ): ResponseEntity<ApiErrorResponse> {
        log.warn("message: ${ex.message}, cause: ${ex.cause}, ex: $ex")
        ex.printStackTrace()
        return ResponseEntity(
                ApiErrorResponse.of(
                        exception = ex,
                        errorCode = ex.errorCode.name,
                        errorMessage = ex.logMessage,
                        logLevel = LogLevel.INFO
                ),
                ex.errorCode.status
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(
            ex: Exception,
            request: HttpServletRequest
    ): ResponseEntity<ApiErrorResponse> {
        log.warn("message: ${ex.message}, cause: ${ex.cause}, ex: $ex")
        ex.printStackTrace()
        return ResponseEntity(
                ApiErrorResponse.of(
                        exception = ex,
                        errorCode = ErrorCode.INTERNAL_SERVER_ERROR.name,
                        errorMessage = ErrorCode.INTERNAL_SERVER_ERROR.message,
                        logLevel = LogLevel.ERROR
                ),
                org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}