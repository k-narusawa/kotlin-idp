package com.knarusawa.demo.idp.idpdemo.application.service.user.getAll

import com.knarusawa.demo.idp.idpdemo.domain.repository.user.UserReadModelRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class UserGetAllService(
  private val userReadModelRepository: UserReadModelRepository
) {
  fun execute(): UserGetAllOutputData {
    return UserGetAllOutputData(userReadModelRepository.findAll())
  }
}