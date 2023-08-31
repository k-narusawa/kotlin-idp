package com.knarusawa.idp.infrastructure

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class RepositoryPerformanceAspect {
  companion object {
    val logger = LoggerFactory.getLogger(RepositoryPerformanceAspect::class.java)
  }

  @Around("execution(public * org.springframework.data.jpa.repository.JpaRepository+.*(..)) || execution(* com.knarusawa.demo.idp.idpdemo.infrastructure.db.repository.*.*(..))")
  fun logExecutionTime(joinPoint: ProceedingJoinPoint): Any? {
    val start = System.currentTimeMillis()
    val proceed = joinPoint.proceed()
    val executionTime = System.currentTimeMillis() - start
    val className = joinPoint.signature.declaringTypeName
    val methodName = joinPoint.signature.name
    logger.info("クラス [${className}], メソッド:[${methodName}], 実行時間:[$executionTime ms]")
    return proceed
  }
}