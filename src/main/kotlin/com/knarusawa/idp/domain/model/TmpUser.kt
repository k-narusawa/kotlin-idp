package com.knarusawa.idp.domain.model

import com.knarusawa.idp.domain.value.Code
import com.knarusawa.idp.domain.value.LoginId
import java.io.Serializable

class TmpUser private constructor(
        val loginId: String,
        val code: String,
        val ttl: Long
) : Serializable {
    companion object {
        fun of(loginId: LoginId, ttl: Long?) = TmpUser(
                loginId = loginId.toString(),
                code = Code.generate().toString(),
                ttl = ttl ?: 600L // デフォルトで600秒(10分)
        )
    }
}