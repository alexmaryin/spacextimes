package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.alexmaryin.spacextimes_rx.data.model.Capsule

data class CapsuleLocal(
    @Embedded val capsule: CapsuleWithoutLaunches,
    @Relation(
        parentColumn = "capsuleId",
        entityColumn = "launchId",
        associateBy = Junction(LaunchesToCapsules::class)
    ) var launches: List<LaunchLocal>
) {
    fun toResponse() = with(capsule) {
        Capsule(id, serial, status, type, reuseCount, waterLandings, landLandings, lastUpdate, lastUpdateRu,
            launches.map { it.toResponse() })
    }
}
