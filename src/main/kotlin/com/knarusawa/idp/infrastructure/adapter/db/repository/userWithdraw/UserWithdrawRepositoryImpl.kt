package com.knarusawa.idp.infrastructure.adapter.db.repository.userWithdraw

import com.knarusawa.idp.domain.model.userWithdraw.UserWithdraw
import com.knarusawa.idp.domain.repository.UserWithdrawRepository
import com.knarusawa.idp.infrastructure.adapter.db.record.UserWithdrawRecord

class UserWithdrawRepositoryImpl(
  private val userWithdrawRecordRepository: UserWithdrawRecordRepository
) : UserWithdrawRepository {
  override fun save(userWithdraw: UserWithdraw) {
    userWithdrawRecordRepository.save(UserWithdrawRecord.from(userWithdraw))
  }
}