package ru.alexmaryin.spacextimes_rx.data.room_model.junctions

import androidx.room.Entity

@Entity(
    tableName = "launches_to_core_flights_table",
    primaryKeys = ["launchId", "coreFlightId"]
)
data class LaunchesToCoreFlights(
    val launchId: String,
    val coreFlightId: String,
)
