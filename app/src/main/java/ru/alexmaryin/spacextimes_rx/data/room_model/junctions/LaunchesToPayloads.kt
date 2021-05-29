package ru.alexmaryin.spacextimes_rx.data.room_model.junctions

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "launches_to_payloads_table",
    primaryKeys = ["launchId", "payloadId"],
)
data class LaunchesToPayloads(
    @ColumnInfo(index = true) val launchId: String,
    @ColumnInfo(index = true) val payloadId: String,
)
