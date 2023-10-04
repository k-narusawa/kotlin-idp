package com.knarusawa.idp.infrastructure.adapter.db.repository.userWithdraw

import com.knarusawa.idp.domain.model.UserWithdraw
import com.knarusawa.idp.domain.repository.UserWithdrawRepository
import com.knarusawa.idp.infrastructure.adapter.db.record.UserWithdrawRecord
import org.springframework.stereotype.Repository

@Repository
class UserWithdrawRepositoryImpl(
        private val userWithdrawRecordRepository: UserWithdrawRecordRepository
) : UserWithdrawRepository {
    override fun save(userWithdraw: UserWithdraw) {
        userWithdrawRecordRepository.save(UserWithdrawRecord.from(userWithdraw))
    }
}