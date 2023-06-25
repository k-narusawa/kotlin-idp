package com.knarusawa.demo.idp.idpdemo.domain.model.error

class AppException(
  val errorCode: ErrorCode,
  val logMessage: String
) : RuntimeException() {
  companion object {
    fun of(errorCode: ErrorCode, errorMessage: String) =
      AppException(
        errorCode = errorCode,
        logMessage = errorMessage
      )
  }
}