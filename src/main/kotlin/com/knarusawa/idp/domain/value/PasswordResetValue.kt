package com.knarusawa.idp.domain.value

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo

data class PasswordResetValue(
        val loginId: String
) {
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
    abstract class PasswordResetValueMixIn(
            @JsonProperty("loginId")
            private var loginId: String,
    )
}