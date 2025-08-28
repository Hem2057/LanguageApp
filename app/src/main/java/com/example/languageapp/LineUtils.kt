package com.example.languageapp

object LineUtils {
    fun buildLinesAsBlock(count: Int): String {
        return (1..count).joinToString("\n") { i -> "Line $i" }
    }
}
