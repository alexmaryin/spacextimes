package ru.alexmaryin.spacextimes_rx.data.api.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import ru.alexmaryin.spacextimes_rx.data.model.extra.Failure
import ru.alexmaryin.spacextimes_rx.data.model.extra.PayloadWeight
import ru.alexmaryin.spacextimes_rx.data.model.parts.Thruster
import java.util.*

@ProvidedTypeConverter
class RoomConverters {

    @TypeConverter
    fun fromDate(date: Date?) = date?.time

    @TypeConverter
    fun toDate(dateUnix: Long?) = dateUnix?.let { Date(it) }

    // Next function require a contract for Any inheritances to override toString
    @TypeConverter
    fun fromList(list: List<Any>): String = list.joinToString("\n")

    @TypeConverter
    fun toStringList(source: String): List<String> = source.split("\n")

    @TypeConverter
    fun toIntList(source: String): List<Int> = source.split(",").filter { it.isNotBlank() }.map { it.toInt() }

    private fun <T> String.splitAndTrim(converter: (String) -> T): List<T> =
        split("\n").filter { it.isNotBlank() }.map(converter)

    @TypeConverter
    fun toPayloadWeightList(source: String): List<PayloadWeight> = source.splitAndTrim { PayloadWeight.fromString(it) }

    @TypeConverter
    fun toThrusterList(source: String): List<Thruster> = source.splitAndTrim { Thruster.fromString(it) }

    @TypeConverter
    fun toFailuresList(source: String): List<Failure> = source.splitAndTrim { Failure.fromString(it) }
}