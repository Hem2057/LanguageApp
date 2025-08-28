package com.example.languageapp

object LineUtils {
    fun buildLines(n: Int): List<String> =
        (1..n.coerceAtLeast(0)).map { "Line $it" }

    fun buildLinesAsBlock(n: Int): String =
        buildLines(n).joinToString("\n")
}
