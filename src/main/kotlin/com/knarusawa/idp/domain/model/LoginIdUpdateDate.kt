package com.knarusawa.idp.domain.model

import com.knarusawa.idp.domain.value.LoginId
import com.knarusawa.idp.domain.value.LoginIdUpdateKey
import com.knarusawa.idp.domain.value.LoginIdUpdateValue
import com.knarusawa.idp.domain.value.UserId
import java.io.Serializable

class LoginIdUpdateDate private constructor(
        val key: LoginIdUpdateKey,
        val value: LoginIdUpdateValue,
        val ttl: Long
) : Serializable {

    companion object {
        fun of(userId: UserId, loginId: LoginId) =
                LoginIdUpdateDate(
                        key = LoginIdUpdateKey.generate(),
                        value = LoginIdUpdateValue(userId = userId.toString(), loginId = loginId.toString()),
                        ttl = 1800L
                )
    }

}