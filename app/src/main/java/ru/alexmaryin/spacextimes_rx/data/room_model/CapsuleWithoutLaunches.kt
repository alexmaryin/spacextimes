package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.*
import ru.alexmaryin.spacextimes_rx.data.model.Capsule
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleStatus
import ru.alexmaryin.spacextimes_rx.data.model.enums.CapsuleType

@Entity(tableName = "capsules_table")
data class CapsuleWithoutLaunches(
    @PrimaryKey val id: String,
    val serial: String,
    val status: CapsuleStatus,
    val type: CapsuleType,
    val reuseCount: Int,
    val waterLandings: Int,
    val landLandings: Int,
    val lastUpdate: String?,
    val lastUpdateRu: String?,
)
