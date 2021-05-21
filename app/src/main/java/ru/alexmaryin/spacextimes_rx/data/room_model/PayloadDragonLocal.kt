package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.extra.PayloadDragon

@Entity(tableName = "payload_dragons_table")
data class PayloadDragonLocal(
    @PrimaryKey val id: String,
    @Relation(
        parentColumn = "id",
        entity = CapsuleWithoutLaunches::class,
        entityColumn = "id"
    )
    val capsule: Capsule?,
    val manifest: String?,
    val returnedMassInKg: Float?,
    val returnedMassInLbs: Float?,
    val flightTime: Int?,
    val waterLanding: Boolean?,
    val groundLanding: Boolean?,
) {
    fun toResponse() = PayloadDragon(capsule, manifest, returnedMassInKg, returnedMassInLbs, flightTime, waterLanding, groundLanding)
}
