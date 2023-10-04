package com.knarusawa.idp.domain.model.userActivity

data class ActivityData(
        val value: String?
) {
    override fun toString(): String {
        return this.value ?: "{}"
    }
}
