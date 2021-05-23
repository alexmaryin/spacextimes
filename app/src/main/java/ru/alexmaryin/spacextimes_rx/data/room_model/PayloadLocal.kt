package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Relation
import ru.alexmaryin.spacextimes_rx.data.model.extra.PayloadDragon

data class PayloadLocal(
    @Embedded val payload: PayloadWithoutDragon,
    @Relation(
        parentColumn = "payloadDragonId",
        entityColumn = "payloadDragonId"
    ) val dragon: PayloadDragonWithoutCapsule?,
) {
    fun toResponse() = payload.toResponse().also { payload ->
        payload.dragon = dragon?.toResponse() ?: PayloadDragon()
    }
}
