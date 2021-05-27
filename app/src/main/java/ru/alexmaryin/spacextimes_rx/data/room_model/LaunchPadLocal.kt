package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Relation

data class LaunchPadLocal(
    @Embedded val launchPad: LaunchPadWithoutLaunches,
    @Relation(
        parentColumn = "launchPadId",
        entityColumn = "launchPadId",
    ) val launches: List<LaunchWithoutDetails>
) {
    fun toResponse() = launchPad.toResponse().also { launchPad ->
        launchPad.launches = launches.filterNot { it.upcoming }.map { it.toResponse() }
    }
}
