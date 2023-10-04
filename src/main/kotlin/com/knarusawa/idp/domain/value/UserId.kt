package com.knarusawa.idp.domain.value

import java.util.*

data class UserId(
        private val value: String
) {
    companion object {
        fun generate(): UserId {
            return UserId(UUID.randomUUID().toString())
        }
    }

    override fun toString() = value
}
