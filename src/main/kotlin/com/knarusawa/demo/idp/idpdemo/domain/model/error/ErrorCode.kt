package com.knarusawa.demo.idp.idpdemo.domain.model.error

import org.springframework.http.HttpStatus

enum class ErrorCode(val status: HttpStatus, val message: String) {
  BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad Request"),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
}