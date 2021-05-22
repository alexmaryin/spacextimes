package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Relation
import ru.alexmaryin.spacextimes_rx.data.model.Payload

data class PayloadLocal(
    @Embedded val payload: PayloadWithoutDragon,
    @Relation(
        parentColumn = "payloadDragonId",
        entityColumn = "payloadDragonId"
    ) val dragon: PayloadDragonWithoutCapsule,
) {
    fun toResponse() = with(payload) {
        Payload(payloadId, name, type, reused, customers, nationalities, manufacturers, orbit, regime, longitude,
            eccentricity, epoch, dragon.toResponse(), semiAxis, rightAscension, periapsis, apoapsis, inclination, pericenterArg,
            lifeSpan, period, meanMotion, meanAnomaly, massInKg, massInLbs, referenceSystem, norads)
    }
}
