package com.knarusawa.idp.domain.model.tmpUser

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.knarusawa.idp.domain.model.oneTimePassword.Code

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