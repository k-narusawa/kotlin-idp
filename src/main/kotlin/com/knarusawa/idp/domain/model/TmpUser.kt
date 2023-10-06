package com.knarusawa.idp.domain.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
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

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
    abstract class TmpUserMixIn(
            @JsonProperty("loginId")
            private var loginId: String,

            @JsonProperty("code")
            @JsonSubTypes.Type(Code::class)
            private var code: String,

            @JsonProperty("ttl")
            private var ttl: Long
    )
}