package com.knarusawa.idp.domain.model.error

class AppException(
  val errorCode: ErrorCode,
  val logMessage: String
) : RuntimeException()