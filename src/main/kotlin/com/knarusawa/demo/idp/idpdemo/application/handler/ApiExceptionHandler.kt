package com.knarusawa.demo.idp.idpdemo.application.handler

import com.knarusawa.demo.idp.idpdemo.domain.model.error.AppException
import com.knarusawa.demo.idp.idpdemo.domain.model.error.ErrorCode
import com.knarusawa.demo.idp.idpdemo.infrastructure.dto.ApiErrorResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.logging.LogLevel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {
  @ExceptionHandler(AppException::class)
  fun handleAppException(
    ex: AppException,
    request: HttpServletRequest
  ): ResponseEntity<ApiErrorResponse> {
    return ResponseEntity(
      ApiErrorResponse.of(
        exception = ex,
        errorCode = ex.errorCode.name,
        errorMessage = ex.errorCode.message,
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