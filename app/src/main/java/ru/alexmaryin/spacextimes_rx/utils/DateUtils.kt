package ru.alexmaryin.spacextimes_rx.utils

import android.content.Context
import ru.alexmaryin.spacextimes_rx.R
import java.util.*

fun Date.toCalendar(): Calendar = Calendar.getInstance().apply { time = this@toCalendar }

fun halfYearString(context: Context, date: Date) =
    (if(date.toCalendar().get(Calendar.MONTH) < 6) context.getString(R.string.half1_string)
    else context.getString(R.string.half2_string)) +
        date.toCalendar().get(Calendar.YEAR).toString()

fun quarterYearString(context: Context, date: Date) = when(date.toCalendar().get(Calendar.MONTH)) {
    in 0..2 -> context.getString(R.string.quarter1_string)
    in 3..5 -> context.getString(R.string.quarter2_string)
    in 6..8 -> context.getString(R.string.quarter3_string)
    else -> context.getString(R.string.quarter4_string)
} + date.toCalendar().get(Calendar.YEAR).toString()