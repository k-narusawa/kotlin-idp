package com.knarusawa.idp.domain.port

interface MailSender {
  fun send(to: String, subject: String, body: String)
}