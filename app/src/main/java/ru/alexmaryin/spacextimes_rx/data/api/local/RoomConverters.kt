package ru.alexmaryin.spacextimes_rx.data.api.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import java.util.*

@ProvidedTypeConverter
class RoomConverters {
    @TypeConverter
    fun fromDate(date: Date) = date.time

    @TypeConverter
    fun toDate(dateUnix: Long) = Date(dateUnix)
}