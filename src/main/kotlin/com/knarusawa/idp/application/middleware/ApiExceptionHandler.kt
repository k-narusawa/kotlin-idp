package com.knarusawa.idp.application.middleware

import com.knarusawa.idp.domain.model.error.ErrorCode
import com.knarusawa.idp.domain.model.error.IdpAppException
import com.knarusawa.idp.infrastructure.dto.response.ApiErrorResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.logging.LogLevel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {
  @ExceptionHandler(IdpAppException::class)
  fun handleIdpAppException(
    ex: IdpAppException,
    request: HttpServletRequest
  ): ResponseEntity<ApiErrorResponse> {
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