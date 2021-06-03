package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.LaunchesToLandingPads

data class LandingPadLocal(
    @Embedded val landingPad: LandingPadWithoutLaunches,
    @Relation(
        parentColumn = "landingPadId",
        entityColumn = "launchId",
        associateBy = Junction(LaunchesToLandingPads::class)
    ) val launches: List<LaunchWithoutDetails> = emptyList(),
) {
    fun toResponse() = landingPad.toResponse().also { landingPad ->
        landingPad.launches = launches.filterNot { it.upcoming }.map { it.toResponse() }
    }
}
