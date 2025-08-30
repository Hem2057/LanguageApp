package com.example.languageapp

import org.junit.Assert.assertEquals
import org.junit.Test

class LineUtilsTest {

    @Test
    fun testBuildLinesAsBlock() {
        val result = LineUtils.buildLinesAsBlock(5)
        val lines = result.split("\n")
        assertEquals(5, lines.size)
    }
}
