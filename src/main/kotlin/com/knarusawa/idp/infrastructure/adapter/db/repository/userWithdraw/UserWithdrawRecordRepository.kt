package com.knarusawa.idp.infrastructure.adapter.db.repository.userWithdraw

import com.knarusawa.idp.infrastructure.adapter.db.record.UserWithdrawRecord
import org.springframework.data.repository.CrudRepository

interface UserWithdrawRecordRepository : CrudRepository<UserWithdrawRecord, Int>