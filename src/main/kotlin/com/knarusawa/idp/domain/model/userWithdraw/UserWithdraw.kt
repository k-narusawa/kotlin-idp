package com.knarusawa.idp.domain.model.userWithdraw

import com.knarusawa.idp.domain.model.user.LoginId
import com.knarusawa.idp.domain.model.user.UserId
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