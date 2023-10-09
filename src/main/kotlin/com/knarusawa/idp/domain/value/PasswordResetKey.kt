package com.knarusawa.idp.domain.value

import com.knarusawa.idp.util.StringUtil
import java.io.Serializable

data class PasswordResetKey(
        private val value: String
) : Serializable {
    companion object {
        private const val PREFIX = "LOGIN_ID_UPDATE_"
        fun generate() = PasswordResetKey(value = "${PREFIX}${StringUtil.generateRandomNumberString(6)}")
        fun fromCode(code: String) = LoginIdUpdateKey(value = "${PREFIX}${code}")
    }

    fun getCode() = value.removePrefix(PREFIX)

    override fun toString(): String {
        return value
    }
}