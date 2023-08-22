package com.knarusawa.demo.idp.idpdemo.domain.model.user

class UserEvent {
    class CreateEvent(user: User)
    class UpdateEvent(user: User)
    class DeleteEvent(user: User)
}