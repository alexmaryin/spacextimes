package ru.alexmaryin.spacextimes_rx.utils

import android.content.Context
import android.os.Build
import ru.alexmaryin.spacextimes_rx.R
import java.util.*
import kotlin.time.*

const val MILLIS_IN_DAY = 86400000L

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

@ExperimentalTime
fun Int.prettifySecondsPeriod(res: Context): String = Duration.seconds(this).toComponents { days, hours, minutes, seconds, _ ->
    listOfNotNull(
        if(days > 0) res.resources.getQuantityString(R.plurals.days_count, days, days) else null,
        if(hours > 0) res.resources.getQuantityString(R.plurals.hours_count, hours, hours) else null,
        if(minutes > 0) res.resources.getQuantityString(R.plurals.minutes_count, minutes, minutes) else null,
        if(seconds > 0) res.resources.getQuantityString(R.plurals.seconds_count, seconds, seconds) else null,
    ).joinToString(" ")
}

@ExperimentalTime
fun Double.prettifyMinutesPeriod(res: Context): String = Duration.minutes(this).toComponents { days, hours, minutes, seconds, _ ->
    listOfNotNull(
        if(days > 0) res.resources.getQuantityString(R.plurals.days_count, days, days) else null,
        if(hours > 0) res.resources.getQuantityString(R.plurals.hours_count, hours, hours) else null,
        if(minutes > 0) res.resources.getQuantityString(R.plurals.minutes_count, minutes, minutes) else null,
        if(seconds > 0) res.resources.getQuantityString(R.plurals.seconds_count, seconds, seconds) else null,
    ).joinToString(" ")
}

@ExperimentalTime
fun Long.prettifyMillisecondsPeriod(res: Context): String = Duration.milliseconds(this).toComponents { days, hours, minutes, seconds, _ ->
    listOfNotNull(
        if(days > 0) res.resources.getQuantityString(R.plurals.days_count, days, days) else null,
        if(hours > 0) res.resources.getQuantityString(R.plurals.hours_count, hours, hours) else null,
        if(minutes > 0) res.resources.getQuantityString(R.plurals.minutes_count, minutes, minutes) else null,
        if(seconds > 0) res.resources.getQuantityString(R.plurals.seconds_count, seconds, seconds) else null,
    ).joinToString(" ")
}

fun Long.scheduledToNextDay() = this in (0..MILLIS_IN_DAY) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
