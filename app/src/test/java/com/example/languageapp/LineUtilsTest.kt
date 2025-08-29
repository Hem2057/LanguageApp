package com.example.languageapp

import org.junit.Assert.assertEquals
import org.junit.Test

class LineUtilsTest {
    @Test
    fun buildsFiveLines() {
        val s = LineUtils.buildLinesAsBlock(5)
        assertEquals(5, s.lines().size)
    }
}
