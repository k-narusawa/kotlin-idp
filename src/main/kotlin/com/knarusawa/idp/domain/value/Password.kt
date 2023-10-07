package com.knarusawa.idp.domain.value

import java.io.Serializable

data class Password(
        private val value: String?
) : Serializable {
    override fun toString() = value ?: ""
}
