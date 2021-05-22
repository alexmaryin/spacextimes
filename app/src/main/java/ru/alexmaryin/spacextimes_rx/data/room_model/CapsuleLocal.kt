package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CapsuleLocal(
    @Embedded val capsule: CapsuleWithoutLaunches,
    @Relation(
        parentColumn = "capsuleId",
        entityColumn = "launchId",
        associateBy = Junction(LaunchesToCapsules::class)
    ) var launches: List<LaunchWithoutDetails>
) {
    fun toResponse() = capsule.toResponse().also { capsule ->
        capsule.launches = launches.map { it.toResponse() }
    }
}
