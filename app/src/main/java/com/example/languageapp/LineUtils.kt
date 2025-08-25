package com.example.languageapp

import org.junit.Assert.assertEquals
import org.junit.Test

class LineUtilsTest {

    @Test
    fun returnsExactCount() {
        val n = 5
        assertEquals(n, LineUtils.buildLines(n).size)
    }

    @Test
    fun zeroAndNegativeBecomeEmpty() {
        assertEquals(0, LineUtils.buildLines(0).size)
        assertEquals(0, LineUtils.buildLines(-2).size)
    }

    @Test
    fun contentMatches() {
        assertEquals(listOf("Line 1", "Line 2", "Line 3"), LineUtils.buildLines(3))
    }
}
