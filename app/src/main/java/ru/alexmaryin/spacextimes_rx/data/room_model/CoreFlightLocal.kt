package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Relation

data class CoreFlightLocal(
    @Embedded val coreFlight: CoreFlightWithoutDetails,
    @Relation(
        parentColumn = "coreId",
        entityColumn = "coreId",
    ) val core: CoreWithoutLaunches,
) {
    fun toResponse() = coreFlight.toResponse(core.toResponse())
}
