package com.knarusawa.idp.domain.model

import com.knarusawa.idp.domain.value.ErrorCode

class IdpAppException(
        val errorCode: ErrorCode,
        val logMessage: String
) : RuntimeException()