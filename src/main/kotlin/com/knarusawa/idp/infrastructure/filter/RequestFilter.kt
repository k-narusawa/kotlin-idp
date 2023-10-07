package com.knarusawa.idp.infrastructure.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class RequestFilter : OncePerRequestFilter() {
    companion object {
        val excludeUrlRegexList = listOf(
                Regex(".*webjars.*"),
                Regex(".*csv.*"),
                Regex(".*\\.png"),
        )
    }

    override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
    ) {
        logger.info("リクエスト受信 リクエストID:[${request.requestId}], メソッド:[${request.method}], URI:[${request.requestURI}]")

        val start = System.currentTimeMillis()
        try {
            filterChain.doFilter(request, response)
        } finally {
            val end = System.currentTimeMillis()
            logger.info("レスポンス返却 リクエストID:[${request.requestId}], メソッド:[${request.method}], URI:[${request.requestURI}], ステータス:[${response.status}], レスポンスタイム:[${end - start}ms]")
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI
        return excludeUrlRegexList.any { path.matches(it) }
    }
}