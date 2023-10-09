package com.knarusawa.idp.domain.value

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.io.Serializable

data class LoginIdUpdateValue(
        val userId: String,
        val loginId: String
) : Serializable {
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
    abstract class LoginIdUpdateValueMixIn(
            @JsonProperty("userId")
            private var userId: String,

            @JsonProperty("loginId")
            private var loginId: String,
    )
}
