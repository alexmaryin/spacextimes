package ru.alexmaryin.spacextimes_rx.utils

import android.content.Context
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before

class DateUtilsKtTest {

    private lateinit var instrumentationContext: Context
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy")

    @Before
    fun setup() {
        instrumentationContext = InstrumentationRegistry.getInstrumentation().context
    }

    @Test
    fun `half year should return 1 for Jan to Jun`() {
        val testDate = dateFormat.parse("13.01.2021")
        assertEquals("первая половина 2021", halfYearString(instrumentationContext, testDate!!))
    }

    @Test
    fun `half year should return 2 for Jul to Dec`() {
        val testDate = dateFormat.parse("01.07.2021")
        assertEquals("вторая половина 2021", halfYearString(instrumentationContext, testDate!!))
    }

    @Test
    fun `quarter year should return 1 for Jan to Mar`() {
        val testDate = dateFormat.parse("25.03.2021")
        assertEquals("I четверть 2021", quarterYearString(instrumentationContext, testDate!!))
    }

    @Test
    fun `quarter year should return 2 for Apr to Jun`() {
        val testDate = dateFormat.parse("25.05.2021")
        assertEquals("II четверть 2021", quarterYearString(instrumentationContext, testDate!!))
    }

    @Test
    fun `quarter year should return 3 for Jul to Sep`() {
        val testDate = dateFormat.parse("01.09.2021")
        assertEquals("III четверть 2021", quarterYearString(instrumentationContext, testDate!!))
    }

    @Test
    fun `quarter year should return 4 for Oct to Dec`() {
        val testDate = dateFormat.parse("16.11.2021")
        assertEquals("IV четверть 2021", quarterYearString(instrumentationContext, testDate!!))
    }
}