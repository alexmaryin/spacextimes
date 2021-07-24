package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.LaunchesToCrew

data class CrewFlightLocal(
    @Embedded val crewFlight: CrewFlightWithoutDetails,
    @Relation(
        parentColumn = "crewId",
        entityColumn = "crewId",
    ) val crew: CrewWithoutLaunches,
    @Relation(
        parentColumn = "launchId",
        entityColumn = "launchId",
    ) val launches: List<LaunchWithoutDetails>,
) {
    fun toResponse() = crewFlight.toResponse(crew.toResponse()).apply {
        member.launches = launches.map { it.toResponse() }
    }
}
