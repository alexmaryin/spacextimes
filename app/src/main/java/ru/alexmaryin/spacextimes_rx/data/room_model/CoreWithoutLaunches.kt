package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.alexmaryin.spacextimes_rx.data.model.Core
import ru.alexmaryin.spacextimes_rx.data.model.enums.CoreStatus

@Entity(tableName = "cores_table")
data class CoreWithoutLaunches(
    @PrimaryKey val coreId: String,
    val serial: String,
    val block: Int?,
    val status: CoreStatus,
    val reuseCount: Int,
    val groundLandAttempts: Int,
    val groundLandings: Int,
    val waterLandAttempts: Int,
    val waterLandings: Int,
    val lastUpdate: String?,
    val lastUpdateRu: String?,
){
    fun toResponse() = Core(coreId, serial, block, status, reuseCount, groundLandAttempts, groundLandings,
        waterLandAttempts, waterLandings, lastUpdate, lastUpdateRu)
}
