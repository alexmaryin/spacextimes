package ru.alexmaryin.spacextimes_rx.utils

import android.content.Context
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import ru.alexmaryin.spacextimes_rx.R
import java.text.SimpleDateFormat

@RunWith(MockitoJUnitRunner::class)
class DateUtilsKtTest {

    @Mock
    private lateinit var context: Context
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy")

    @Before
    fun setup() {
        `when`(context.getString(R.string.year_suffix)).thenReturn(" г.")
        `when`(context.getString(R.string.half1_string)).thenReturn("первая половина ")
        `when`(context.getString(R.string.half2_string)).thenReturn("вторая половина ")
        `when`(context.getString(R.string.quarter1_string)).thenReturn("I четверть ")
        `when`(context.getString(R.string.quarter2_string)).thenReturn("II четверть ")
        `when`(context.getString(R.string.quarter3_string)).thenReturn("III четверть ")
        `when`(context.getString(R.string.quarter4_string)).thenReturn("IV четверть ")
    }

    @Test
    fun `half year should return 1 for Jan to Jun`() {
        val testDate = dateFormat.parse("13.01.2021")
        assertEquals("первая половина 2021 г.", halfYearString(context, testDate!!))
    }

    @Test
    fun `half year should return 2 for Jul to Dec`() {
        val testDate = dateFormat.parse("01.07.2021")
        assertEquals("вторая половина 2021 г.", halfYearString(context, testDate!!))
    }

    @Test
    fun `quarter year should return 1 for Jan to Mar`() {
        val testDate = dateFormat.parse("25.03.2021")
        assertEquals("I четверть 2021 г.", quarterYearString(context, testDate!!))
    }

    @Test
    fun `quarter year should return 2 for Apr to Jun`() {
        val testDate = dateFormat.parse("25.05.2021")
        assertEquals("II четверть 2021 г.", quarterYearString(context, testDate!!))
    }

    @Test
    fun `quarter year should return 3 for Jul to Sep`() {
        val testDate = dateFormat.parse("01.09.2021")
        assertEquals("III четверть 2021 г.", quarterYearString(context, testDate!!))
    }

    @Test
    fun `quarter year should return 4 for Oct to Dec`() {
        val testDate = dateFormat.parse("16.11.2021")
        assertEquals("IV четверть 2021 г.", quarterYearString(context, testDate!!))
    }
}