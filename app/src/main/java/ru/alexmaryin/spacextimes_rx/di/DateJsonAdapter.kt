package ru.alexmaryin.spacextimes_rx.di

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


val DATE_FORMATS = arrayOf(
    "yyyy-MM-dd",
    "yyyy-MM-dd'T'HH:mm:ss.sss'Z'",
    "yyyy-MM-dd'T'HH:mm:ssZZZZZ"
)

class DateJsonAdapter {

    private val dateFormatters = DATE_FORMATS.map { SimpleDateFormat(it, Locale.US) }

    @FromJson
    fun deserialize(json: String?): Date? {
        var parsedDate: Date? = null
        json?.let {
            dateFormatters.forEach { formatter ->
                try {
                    parsedDate = formatter.parse(it)
                } catch (ignore: ParseException) {}
            }
            return parsedDate
        }
        return null
    }

    @ToJson
    fun serialize(src: Date?) = dateFormatters.last().format(src ?: Calendar.getInstance())
}