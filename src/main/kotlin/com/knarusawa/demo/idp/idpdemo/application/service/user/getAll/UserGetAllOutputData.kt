package com.knarusawa.demo.idp.idpdemo.application.service.user.getAll

import com.knarusawa.demo.idp.idpdemo.domain.model.user.UserReadModel

data class UserGetAllOutputData(
  val users: List<UserReadModel>
)
