package com.knarusawa.idp.domain.model.oneTimePassword

import java.util.*

data class Code(
        val value: String
) {
    companion object {
        fun generate(): Code {
            val random = Random()
            val sb = StringBuilder()
            for (i in 0..5) {
                sb.append(random.nextInt(10))
            }
            return Code(sb.toString())
        }
    }

    override fun toString(): String {
        return this.value
    }
}
