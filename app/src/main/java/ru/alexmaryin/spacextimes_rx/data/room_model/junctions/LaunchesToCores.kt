package ru.alexmaryin.spacextimes_rx.data.room_model.junctions

import androidx.room.Entity

@Entity(
    tableName = "launches_to_cores_table",
    primaryKeys = ["launchId", "coreId"],
)
data class LaunchesToCores(
    val launchId: String,
    val coreId: String,
)