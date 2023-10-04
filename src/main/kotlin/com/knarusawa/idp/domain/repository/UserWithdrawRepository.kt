package com.knarusawa.idp.domain.repository

import com.knarusawa.idp.domain.model.UserWithdraw

interface UserWithdrawRepository {
    fun save(userWithdraw: UserWithdraw)
}