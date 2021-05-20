package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleStatus
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleType

@Entity(tableName = "capsules_table")
data class CapsuleLocal(
    @PrimaryKey val id: String,
    val serial: String,
    val status: CapsuleStatus,
    val type: CapsuleType,
    val reuseCount: Int,
    val waterLandings: Int,
    val landLandings: Int,
    val lastUpdate: String?,
    val lastUpdateRu: String?,
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
    fun toResponse() = Capsule(id, serial, status, type, reuseCount, waterLandings, landLandings, lastUpdate, lastUpdateRu,
        launches.map { it.toResponse() })
}
