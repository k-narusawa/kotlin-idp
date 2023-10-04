package com.knarusawa.idp.domain.value

data class ActivityData(
        private val value: String?
) {
    override fun toString(): String {
        return this.value ?: "{}"
    }
}
