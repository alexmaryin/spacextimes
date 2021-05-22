package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Relation
import ru.alexmaryin.spacextimes_rx.data.model.extra.PayloadDragon

data class PayloadDragonLocal(
    @Embedded val payloadDragon: PayloadDragonWithoutCapsule,
    @Relation(
        parentColumn = "capsuleId",
        entityColumn = "capsuleId"
    ) val capsule: CapsuleLocal?
) {
    fun toResponse() = payloadDragon.toResponse().also { it.capsule = capsule?.toResponse() }
}
