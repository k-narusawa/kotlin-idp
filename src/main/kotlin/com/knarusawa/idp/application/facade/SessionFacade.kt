package com.knarusawa.idp.application.facade

import com.knarusawa.idp.infrastructure.middleware.logger
import org.springframework.session.FindByIndexNameSessionRepository
import org.springframework.session.Session
import org.springframework.stereotype.Component

@Component
class SessionFacade(
        private val sessionRepository: FindByIndexNameSessionRepository<out Session>,
) {
    private val log = logger()
    fun deleteUserSessions(username: String) {
        log.info("セッション削除試行")
        val sessions = sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, username)
        sessions.values.forEach { session ->
            log.info("セッション削除 セッションID: [${session.id}]")
            sessionRepository.deleteById(session.id)
        }
    }
}