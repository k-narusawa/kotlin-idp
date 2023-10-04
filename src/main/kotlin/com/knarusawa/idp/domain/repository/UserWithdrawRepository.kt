package com.knarusawa.idp.domain.repository

import com.knarusawa.idp.domain.model.userWithdraw.UserWithdraw

interface UserWithdrawRepository {
    fun save(userWithdraw: UserWithdraw)
}