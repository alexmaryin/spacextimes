package ru.alexmaryin.spacextimes_rx.data.room_model.junctions

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "launches_to_landing_pads_table",
    primaryKeys = ["launchId", "landingPadId"],
)
data class LaunchesToLandingPads(
    @ColumnInfo(index = true) val launchId: String,
    @ColumnInfo(index = true) val landingPadId: String,
)