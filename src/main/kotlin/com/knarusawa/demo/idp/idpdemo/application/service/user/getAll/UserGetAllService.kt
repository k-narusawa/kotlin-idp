package com.knarusawa.demo.idp.idpdemo.application.service.user.getAll

import com.knarusawa.demo.idp.idpdemo.domain.model.user.UserReadModel
import com.knarusawa.demo.idp.idpdemo.domain.repository.user.UserReadModelRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class UserGetAllService(
  private val userReadModelRepository: UserReadModelRepository
) {
  fun execute(): List<UserReadModel> {
    return userReadModelRepository.findAll()
  }
}