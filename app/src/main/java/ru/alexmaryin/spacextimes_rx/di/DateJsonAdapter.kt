package ru.alexmaryin.spacextimes_rx.di

import android.util.Log
import com.google.gson.*
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


val DATE_FORMATS = arrayOf(
    "yyyy-MM-dd",
    "yyyy-MM-dd'T'HH:mm:ss.sss'Z'",
    "yyyy-MM-dd'T'HH:mm:ssX"
)

object DateJsonAdapter : JsonDeserializer<Date>, JsonSerializer<Date> {

    private val dateFormatters = DATE_FORMATS.map { SimpleDateFormat(it, Locale.US) }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date? {
        var parsedDate: Date? = null
        json?.let {
            dateFormatters.forEach { formatter ->
                try {
                    parsedDate = formatter.parse(it.asString)
                    Log.d("DATE", "Successful parsed date: ${parsedDate.toString()}")
                } catch (ignore: ParseException) {}
            }
        }
        return parsedDate
    }

    override fun serialize(src: Date?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        val dateFormatAsString = dateFormatters[0].format(src ?: Calendar.getInstance())
        return JsonPrimitive(dateFormatAsString)
    }
}