package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.LaunchesToCoreFlights

data class CoreFlightLocal(
    @Embedded val coreFlight: CoreFlightWithoutDetails,
    @Relation(
        parentColumn = "coreId",
        entityColumn = "coreId",
    ) val core: CoreWithoutLaunches,
    @Relation(
        parentColumn = "coreFlightId",
        entityColumn = "launchId",
        associateBy = Junction(LaunchesToCoreFlights::class)
    ) val launches: List<LaunchWithoutDetails>,
) {
    fun toResponse() = coreFlight.toResponse(core.toResponse()).apply {
        core?.launches = launches.map { it.toResponse() }
    }
}
