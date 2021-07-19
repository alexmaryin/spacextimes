package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.LaunchesToCrew

data class LaunchCrewLocal(
    @Embedded val launchCrew: LaunchCrewWithoutDetails,
    @Relation(
        parentColumn = "crewId",
        entityColumn = "crewId",
    ) val crew: CrewWithoutLaunches,
    @Relation(
        parentColumn = "crewId",
        entityColumn = "launchId",
        associateBy = Junction(LaunchesToCrew::class)
    ) val launches: List<LaunchWithoutDetails>,
) {
    fun toResponse() = launchCrew.toResponse(crew.toResponse()).apply {
        member.launches = launches.map { it.toResponse() }
    }
}
