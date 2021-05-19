package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleStatus
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleType
import ru.alexmaryin.spacextimes_rx.data.model.lists.Capsules

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
) {
    fun toResponseList() = Capsules(id, serial, status, type, reuseCount, waterLandings, landLandings, lastUpdate, lastUpdateRu, emptyList())

    fun toResponseDetail() = Capsule(id, serial, status, type, reuseCount, waterLandings, landLandings, lastUpdate, lastUpdateRu, emptyList())
}
