package com.knarusawa.idp.domain.model.error

class IdpAppException(
  val errorCode: ErrorCode,
  val logMessage: String
) : RuntimeException()