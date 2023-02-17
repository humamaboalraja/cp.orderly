package com.cp.orderly

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UtilsTest {

    private val utils = Utils

    @Test
    fun `should return example`() {
        assertEquals(utils.example(), "example")
    }
}
