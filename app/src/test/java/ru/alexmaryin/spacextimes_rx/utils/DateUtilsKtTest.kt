package ru.alexmaryin.spacextimes_rx.utils

import android.content.Context
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner
import ru.alexmaryin.spacextimes_rx.R
import java.text.SimpleDateFormat

@RunWith(MockitoJUnitRunner::class)
class DateUtilsKtTest {

    @Mock
    private lateinit var context: Context
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy")

    @Test
    fun `half year should return 1 for Jan to Jun`() {
        val testDate = dateFormat.parse("13.01.2021")
        `when`(context.getString(R.string.half1_string)).thenReturn("первая половина ")
        assertEquals("первая половина 2021", halfYearString(context, testDate!!))
    }

    @Test
    fun `half year should return 2 for Jul to Dec`() {
        val testDate = dateFormat.parse("01.07.2021")
        `when`(context.getString(R.string.half2_string)).thenReturn("вторая половина ")
        assertEquals("вторая половина 2021", halfYearString(context, testDate!!))
    }

    @Test
    fun `quarter year should return 1 for Jan to Mar`() {
        val testDate = dateFormat.parse("25.03.2021")
        `when`(context.getString(R.string.quarter1_string)).thenReturn("I четверть ")
        assertEquals("I четверть 2021", quarterYearString(context, testDate!!))
    }

    @Test
    fun `quarter year should return 2 for Apr to Jun`() {
        val testDate = dateFormat.parse("25.05.2021")
        `when`(context.getString(R.string.quarter2_string)).thenReturn("II четверть ")
        assertEquals("II четверть 2021", quarterYearString(context, testDate!!))
    }

    @Test
    fun `quarter year should return 3 for Jul to Sep`() {
        val testDate = dateFormat.parse("01.09.2021")
        `when`(context.getString(R.string.quarter3_string)).thenReturn("III четверть ")
        assertEquals("III четверть 2021", quarterYearString(context, testDate!!))
    }

    @Test
    fun `quarter year should return 4 for Oct to Dec`() {
        val testDate = dateFormat.parse("16.11.2021")
        `when`(context.getString(R.string.quarter4_string)).thenReturn("IV четверть ")
        assertEquals("IV четверть 2021", quarterYearString(context, testDate!!))
    }
}