package com.knarusawa.idp.domain.model.user

data class UserId(
        val value: String
) {
    companion object {
        fun generate(): UserId {
            return UserId(java.util.UUID.randomUUID().toString())
        }
    }

    override fun toString() = value
}
