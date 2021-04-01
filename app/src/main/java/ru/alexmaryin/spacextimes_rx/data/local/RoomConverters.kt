package ru.alexmaryin.spacextimes_rx.data.local

import androidx.room.TypeConverter
import java.util.*

class RoomConverters {
    @TypeConverter
    fun fromDate(date: Date) = date.time

    @TypeConverter
    fun toDate(dateUnix: Long) = Date(dateUnix)
}