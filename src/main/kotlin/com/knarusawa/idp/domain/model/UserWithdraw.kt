package com.knarusawa.idp.domain.model

import com.knarusawa.idp.domain.value.LoginId
import com.knarusawa.idp.domain.value.UserId
import java.time.LocalDateTime

class UserWithdraw private constructor(
        val userId: UserId,
        val originalLoginId: LoginId,
        val withdrawnAt: LocalDateTime
) {
    companion object {
        fun of(userId: UserId, loginId: LoginId) = UserWithdraw(
                userId = userId,
                originalLoginId = loginId,
                withdrawnAt = LocalDateTime.now()
        )
    }
}