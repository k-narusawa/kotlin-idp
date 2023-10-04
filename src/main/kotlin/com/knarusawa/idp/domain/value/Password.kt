package com.knarusawa.idp.domain.value

data class Password(
        private val value: String?
) {
    override fun toString() = value ?: ""
}
