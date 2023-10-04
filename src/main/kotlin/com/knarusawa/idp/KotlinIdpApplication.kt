package com.knarusawa.idp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinIdpApplication

fun main(args: Array<String>) {
    runApplication<KotlinIdpApplication>(*args)
}
