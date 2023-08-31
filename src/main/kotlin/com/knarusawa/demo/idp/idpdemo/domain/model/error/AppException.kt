package com.knarusawa.demo.idp.idpdemo.domain.model.error

class AppException(
  val errorCode: ErrorCode,
  val logMessage: String
) : RuntimeException()