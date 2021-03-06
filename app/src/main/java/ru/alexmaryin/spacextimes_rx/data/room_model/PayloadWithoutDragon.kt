package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexmaryin.spacextimes_rx.data.model.Payload
import ru.alexmaryin.spacextimes_rx.data.model.enums.PayloadType
import ru.alexmaryin.spacextimes_rx.data.model.extra.PayloadDragon
import java.util.*

@Entity(tableName = "payloads_table")
data class PayloadWithoutDragon(
    @PrimaryKey val payloadId: String,
    val name: String?,
    val type: PayloadType?,
    val reused: Boolean,
    val customers: List<String>,
    val nationalities: List<String>,
    val manufacturers: List<String>,
    val orbit: String?,
    val regime: String?,
    val longitude: Float?,
    val eccentricity: Float?,
    val epoch: Date?,
    val payloadDragonId: String?,
    val semiAxis: Float?,
    val rightAscension: Float?,
    val periapsis: Float?,
    val apoapsis: Float?,
    val inclination: Float?,
    val pericenterArg: Float?,
    val lifeSpan: Float?,
    val period: Float?,
    val meanMotion: Float?,
    val meanAnomaly: Float?,
    val massInKg: Float?,
    val massInLbs: Float?,
    val referenceSystem: String?,
    val norads: List<Int> = emptyList(),
) {
    fun toResponse() = Payload(payloadId, name, type, reused, customers, nationalities, manufacturers, orbit, regime, longitude,
        eccentricity, epoch, PayloadDragon(), semiAxis, rightAscension, periapsis, apoapsis, inclination, pericenterArg, lifeSpan, period,
        meanMotion, meanAnomaly, massInKg, massInLbs, referenceSystem, norads)
}
