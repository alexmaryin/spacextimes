package ru.alexmaryin.spacextimes_rx.data.room_model

import androidx.room.Entity

@Entity(
    tableName = "launches_to_payloads_table",
    primaryKeys = ["launchId", "payloadId"],
)
data class LaunchesToPayloads(
    val launchId: String,
    val payloadId: String,
)
