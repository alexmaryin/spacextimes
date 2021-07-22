package ru.alexmaryin.spacextimes_rx.data.api.local

import org.junit.Assert.assertEquals
import org.junit.Test

class RoomConvertersTest {

    private val convertor = RoomConverters()

    @Test
    fun `Any list should be converted to strings with newline delimiter`() {
        val source = listOf(44155, 44156, 44157, 44158)
        val result = "44155\n44156\n44157\n44158"
        assertEquals(result, convertor.fromList(source))
    }

    @Test
    fun `String with integers should be converted to list of Int`() {
        val source = "44155\n44156\n44157\n44158"
        val result = listOf(44155, 44156, 44157, 44158)
        assertEquals(result, convertor.toIntList(source))
    }
}