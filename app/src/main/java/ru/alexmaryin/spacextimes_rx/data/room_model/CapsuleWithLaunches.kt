package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.alexmaryin.spacextimes_rx.data.model.Capsule

data class CapsuleWithLaunches(
    @Embedded val capsule: CapsuleLocal,
    @Relation(
        parentColumn = "id",
        entity = LaunchLocal::class,
        entityColumn = "id",
        associateBy = Junction(
            value = LaunchesToCapsules::class,
            parentColumn = "capsuleId",
            entityColumn = "launchId"
        )
    ) var launches: List<LaunchLocal>
) {
    fun toResponse() = with(capsule) {
        Capsule(id, serial, status, type, reuseCount, waterLandings, landLandings, lastUpdate, lastUpdateRu,
            launches.map { it.toResponse() })
    }
}
