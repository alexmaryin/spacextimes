package ru.alexmaryin.spacextimes_rx.data.api.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import ru.alexmaryin.spacextimes_rx.data.model.enums.OrbitType
import ru.alexmaryin.spacextimes_rx.data.model.extra.Failure
import ru.alexmaryin.spacextimes_rx.data.model.extra.PayloadWeight
import ru.alexmaryin.spacextimes_rx.data.model.extra.Thrust
import ru.alexmaryin.spacextimes_rx.data.model.parts.Thruster
import java.util.*

@ProvidedTypeConverter
class RoomConverters {
    @TypeConverter
    fun fromDate(date: Date?) = date?.time

    @TypeConverter
    fun toDate(dateUnix: Long?) = dateUnix?.let { Date(it) }

    @TypeConverter
    fun fromStringList(list: List<String>): String = list.joinToString("\n")

    @TypeConverter
    fun toStringList(source: String): List<String> = source.split("\n")

    @TypeConverter
    fun fromIntList(list: List<Int>): String = list.joinToString(",")

    @TypeConverter
    fun toIntList(source: String): List<Int> = source.split(",").map { it.toInt() }

    @TypeConverter
    fun fromPayloadWeightList(source: List<PayloadWeight>): String = source.joinToString("\n") {
        "${it.id.name}:::${it.name}:::${it.kg}:::${it.lb}"
    }

    @TypeConverter
    fun toPayloadWeightList(source: String): List<PayloadWeight> = source.split("\n").map {
        it.split(":::").run {
            PayloadWeight(
                id = OrbitType.valueOf(get(0)),
                name = get(1),
                kg = get(2).toFloat(),
                lb = get(3).toFloat()
            )
        }
    }

    @TypeConverter
    fun fromThrusterList(source: List<Thruster>): String = source.joinToString("\n") {
        "${it.type}:::${it.amount}:::${it.pods}:::${it.isp}:::${it.thrust.kN}:::${it.thrust.lbf}:::${it.hotComponent}:::${it.oxidizerComponent}"
    }

    @TypeConverter
    fun toThrusterList(source: String): List<Thruster> = source.split("\n").map {
        it.split(":::").run {
            Thruster(
                type = get(0),
                amount = get(1).toInt(),
                pods = get(2).toInt(),
                isp = get(3).toInt(),
                thrust = Thrust(kN = get(4).toFloat(), lbf = get(5).toInt()),
                hotComponent = get(6),
                oxidizerComponent = get(7)
            )
        }
    }

    @TypeConverter
    fun fromFailuresList(source: List<Failure>): String = source.joinToString("\n") {
        "${it.time}:::${it.altitude}:::${it.reason}"
    }

    @TypeConverter
    fun toFailuresList(source: String): List<Failure> = source.split("\n").map {
        it.split(":::").run {
            Failure(
                time = get(0).toInt(),
                altitude = get(1).toInt(),
                reason = get(2)
            )
        }
    }
}