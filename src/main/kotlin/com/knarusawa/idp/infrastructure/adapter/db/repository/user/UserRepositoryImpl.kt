package com.knarusawa.idp.infrastructure.adapter.db.repository.user

import com.knarusawa.idp.domain.model.User
import com.knarusawa.idp.domain.repository.UserRepository
import org.springframework.stereotype.Repository


@Repository
class UserRepositoryImpl(
        private val userRecordRepository: UserRecordRepository
) : UserRepository {
    override fun save(user: User) {
        userRecordRepository.save(user.toRecord())
    }

    override fun findByUserId(userId: String): User? {
        val record = userRecordRepository.findByUserId(userId = userId)
        return record?.let { User.from(it) }
    }

    override fun findByLoginId(loginId: String): User? {
        val record = userRecordRepository.findByLoginId(loginId = loginId)
        return record?.let { User.from(it) }
    }

    override fun deleteByUserId(userId: String) {
        userRecordRepository.deleteByUserId(userId = userId)
    }
}