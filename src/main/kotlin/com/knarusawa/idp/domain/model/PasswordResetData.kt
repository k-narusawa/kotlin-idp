package com.knarusawa.idp.domain.model

import com.knarusawa.idp.domain.value.LoginId
import com.knarusawa.idp.domain.value.PasswordResetKey
import com.knarusawa.idp.domain.value.PasswordResetValue
import java.io.Serializable

class PasswordResetData private constructor(
        val key: PasswordResetKey,
        val value: PasswordResetValue,
        val ttl: Long
) : Serializable {
    companion object {
        fun of(loginId: LoginId) =
                PasswordResetData(
                        key = PasswordResetKey.generate(),
                        value = PasswordResetValue(loginId = loginId.toString()),
                        ttl = 1800L
                )
    }
}