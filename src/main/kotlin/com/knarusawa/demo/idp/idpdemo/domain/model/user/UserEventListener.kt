package com.knarusawa.demo.idp.idpdemo.domain.model.user

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserEventListener {
    @EventListener
    fun on(event: UserEvent.CreateEvent) {
        println("UserEventListener.on: $event")
    }
}