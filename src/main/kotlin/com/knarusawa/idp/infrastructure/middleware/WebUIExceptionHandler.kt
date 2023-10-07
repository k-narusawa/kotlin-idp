package com.knarusawa.idp.infrastructure.middleware

import com.knarusawa.idp.domain.model.IdpAppException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class WebUIExceptionHandler {
    private val log = logger()

    @ExceptionHandler(IdpAppException::class)
    fun handleIdpAppException(
            ex: IdpAppException,
            request: HttpServletRequest
    ): String {
        log.warn("message: ${ex.message}, cause: ${ex.cause}, ex: $ex")
        return "error"
    }

    @ExceptionHandler(Exception::class)
    fun handleException(
            ex: IdpAppException,
            request: HttpServletRequest
    ): String {
        log.warn("message: ${ex.message}, cause: ${ex.cause}, ex: $ex")
        return "error"
    }
}