package com.knarusawa.idp.domain.value

import org.springframework.http.HttpStatus

enum class ErrorCode(val status: HttpStatus, val message: String) {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad Request"),
    USER_EXISTS(HttpStatus.BAD_REQUEST, "User Already Exists"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User Not Found"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
}