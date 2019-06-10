package com.tarek360.movies.formatter

import java.lang.StringBuilder

class CastTextFormatterImpl : CastTextFormatter {

    override fun format(list: List<String>): String {
        if (list.isEmpty()) {
            return ""
        }
        val sb = StringBuilder()
        for (index in 0 until list.size - 1) {
            sb.append("- ${list[index]}.\n")
        }
        sb.append("- ${list.last()}.")
        return sb.toString()
    }

}