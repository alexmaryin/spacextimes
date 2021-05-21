package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Entity

@Entity(
    tableName = "launches_to_capsules_table",
    primaryKeys = ["launchId", "capsuleId"],
)
data class LaunchesToCapsules(
    val launchId: String,
    val capsuleId: String,
)
