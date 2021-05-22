package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexmaryin.spacextimes_rx.data.model.extra.PayloadDragon

@Entity(tableName = "payload_dragons_table")
data class PayloadDragonWithoutCapsule(
    @PrimaryKey val payloadDragonId: String,
    val capsuleId: String?,
    val manifest: String?,
    val returnedMassInKg: Float?,
    val returnedMassInLbs: Float?,
    val flightTime: Int?,
    val waterLanding: Boolean?,
    val groundLanding: Boolean?,
) {
    fun toResponse() = PayloadDragon(null, manifest, returnedMassInKg, returnedMassInLbs, flightTime, waterLanding, groundLanding)
}
