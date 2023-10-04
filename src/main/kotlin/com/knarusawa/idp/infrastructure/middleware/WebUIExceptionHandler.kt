package com.knarusawa.idp.infrastructure.middleware

import com.knarusawa.idp.domain.model.error.IdpAppException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class WebUIExceptionHandler {
    @ExceptionHandler(IdpAppException::class)
    fun handleIdpAppException(
            ex: IdpAppException,
            request: HttpServletRequest
    ): String {
        return "error/error"
    }

    @ExceptionHandler(Exception::class)
    fun handleException(
            ex: IdpAppException,
            request: HttpServletRequest
    ): String {
        return "error/error"
    }
}