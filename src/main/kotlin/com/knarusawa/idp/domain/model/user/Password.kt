package com.knarusawa.idp.domain.model.user

data class Password(
        private val value: String?
) {
    override fun toString() = value ?: ""
}
