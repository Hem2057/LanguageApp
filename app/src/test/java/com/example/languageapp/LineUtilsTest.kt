package com.example.languageapp

import org.junit.Assert.assertEquals
import org.junit.Test

class LineUtilsTest {

    @Test
    fun buildsCorrectNumberOfLines() {
        val block = LineUtils.buildLinesAsBlock(5)
        val lines = block.split("\n")
        assertEquals(5, lines.size)
        assertEquals("Line 1", lines.first())
        assertEquals("Line 5", lines.last())
    }
}
