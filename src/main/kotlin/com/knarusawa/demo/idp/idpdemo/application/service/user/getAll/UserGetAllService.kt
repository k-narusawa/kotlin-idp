package com.knarusawa.demo.idp.idpdemo.application.service.user.getAll

import com.knarusawa.demo.idp.idpdemo.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class UserGetAllService(private val userRepository: UserRepository) {
  fun execute(): UserGetAllOutputData {
    return UserGetAllOutputData(userRepository.findAll())
  }
}