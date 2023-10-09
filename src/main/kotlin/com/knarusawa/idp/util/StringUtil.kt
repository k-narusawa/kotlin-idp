package com.knarusawa.idp.util

import java.util.*

object StringUtil {
    fun generateRandomString(digit: Int): String {
        val random = Random()
        val sb = StringBuilder()
        for (i in 0..digit) {
            sb.append(random.nextInt(10))
        }
        return sb.toString()
    }
}