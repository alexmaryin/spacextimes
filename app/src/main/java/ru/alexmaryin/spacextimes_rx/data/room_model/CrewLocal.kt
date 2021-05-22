package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CrewLocal(
    @Embedded val crew: CrewWithoutLaunches,
    @Relation(
        parentColumn = "crewId",
        entityColumn = "launchId",
        associateBy = Junction(LaunchesToCrew::class)
    ) val launches: List<LaunchWithoutDetails>
) {
    fun toResponse() = crew.toResponse().also { crew ->
        crew.launches = launches.map { it.toResponse() }
    }
}