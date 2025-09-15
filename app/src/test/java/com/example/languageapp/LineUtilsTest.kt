
package com.example.languageapp

import org.junit.Assert.assertEquals
import org.junit.Test

class LineUtilsTest {
    @Test
    fun buildLinesAsBlock_generatesCorrectLines() {
        val out = LineUtils.buildLinesAsBlock(3)
        assertEquals("Line 1\nLine 2\nLine 3", out)
    }
}
