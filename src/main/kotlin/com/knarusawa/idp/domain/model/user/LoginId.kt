package com.knarusawa.idp.domain.model.user

data class LoginId(
        private val value: String
) {
    init {
        // RFC5322で定義されているメールアドレス形式に準拠
        val regex =
                Regex("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))\$")

        if (!value.matches(regex))
            throw IllegalArgumentException("メールアドレス形式でない")
    }

    override fun toString() = value
}
