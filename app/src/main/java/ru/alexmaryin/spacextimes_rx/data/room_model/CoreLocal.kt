package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.LaunchesToCores

data class CoreLocal(
    @Embedded val core: CoreWithoutLaunches,
    @Relation(
        parentColumn = "coreId",
        entityColumn = "launchId",
        associateBy = Junction(LaunchesToCores::class)
    ) val launches: List<LaunchWithoutDetails> = emptyList()
) {
    fun toResponse() = core.toResponse().also { core ->
        core.launches = launches.map { it.toResponse() }
    }
}
