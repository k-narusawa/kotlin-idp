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
    logger.debug("request User-Agent: " + request.getHeader("User-Agent"));
    val start = System.currentTimeMillis()
    try {
      filterChain.doFilter(request, response)
    } finally {
      val end = System.currentTimeMillis()
      logger.info("METHOD: ${request.method}, REQUEST_URL: ${request.requestURI}, RESPONSE_TIME: ${end - start}ms")
    }
  }

  override fun shouldNotFilter(request: HttpServletRequest): Boolean {
    val path = request.requestURI
    return excludeUrlRegexList.any { path.matches(it) }
  }
}