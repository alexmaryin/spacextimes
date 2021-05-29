package ru.alexmaryin.spacextimes_rx.data.room_model.junctions

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "launches_to_crew_table",
    primaryKeys = ["launchId", "crewId"],
)
data class LaunchesToCrew(
    @ColumnInfo(index = true) val launchId: String,
    @ColumnInfo(index = true) val crewId: String,
)
