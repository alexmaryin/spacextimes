package ru.alexmaryin.spacextimes_rx.data.room_model.junctions

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "launches_to_capsules_table",
    primaryKeys = ["launchId", "capsuleId"],
)
data class LaunchesToCapsules(
    @ColumnInfo(index = true) val launchId: String,
    @ColumnInfo(index = true) val capsuleId: String,
)
