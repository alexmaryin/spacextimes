package ru.alexmaryin.spacextimes_rx.data.api.local

import androidx.room.TypeConverter
import ru.alexmaryin.spacextimes_rx.data.model.enums.OrbitType
import ru.alexmaryin.spacextimes_rx.data.model.extra.PayloadWeight
import java.util.*

//@ProvidedTypeConverter
class RoomConverters {
    @TypeConverter
    fun fromDate(date: Date) = date.time

    @TypeConverter
    fun toDate(dateUnix: Long) = Date(dateUnix)

    @TypeConverter
    fun fromStringList(list: List<String>): String = list.joinToString("\n")

    @TypeConverter
    fun toStringList(source: String): List<String> = source.split("\n")

    @TypeConverter
    fun fromPayloadWeight(source: PayloadWeight): String = with(source) {
        "${id.name}:::$name:::$kg:::$lb"
    }

    @TypeConverter
    fun toPayloadWeight(source: String): PayloadWeight = source.split(":::").run {
        PayloadWeight(
            id = OrbitType.valueOf(get(0)),
            name = get(1),
            kg = get(2).toFloat(),
            lb = get(3).toFloat()
        )
    }

}