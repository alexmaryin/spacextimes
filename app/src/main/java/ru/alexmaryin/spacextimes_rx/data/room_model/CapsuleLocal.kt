package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.alexmaryin.spacextimes_rx.data.room_model.junctions.LaunchesToCapsules

data class CapsuleLocal(
    @Embedded val capsule: CapsuleWithoutLaunches,
    @Relation(
        parentColumn = "capsuleId",
        entityColumn = "launchId",
        associateBy = Junction(LaunchesToCapsules::class)
    ) var launches: List<LaunchWithoutDetails> = emptyList()
) {
    fun toResponse() = capsule.toResponse().also { capsule ->
        capsule.launches = launches.map { it.toResponse() }
    }
}
